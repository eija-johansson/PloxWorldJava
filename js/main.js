var Main = React.createClass({
	componentDidMount: function () {
		var self = this;
		MessageSystem.subscribe(MessageSystem.selectPlanet, function (selectedPlanetName) {
			self.showPlanet(selectedPlanetName);
		});

		$.ajax({
			url: this.props.startUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	progressTurn: function () {
		$.ajax({
			url: this.props.progressTurnUrl,
			dataType: 'json',
			cache: false,
			success: function (data) {
				//console.log("data: " + JSON.stringify(data));
				this.setState({data: data});
			}.bind(this),
			error: function (xhr, status, err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	showPlanetList: function () {
		this.setState({showPlanetList: true, selectedPlanetName: undefined});
	},
	showPlanet: function (selectedPlanetName) {
		this.setState({showPlanetList: true, selectedPlanetName: selectedPlanetName});
	},
	closePlanetList: function () {
		this.setState({showPlanetList: false});
	},
	render: function () {

		if (!this.state) {
			console.log("laoding");
			return (<div className="main">
				laodin
			</div>);
		} else {
			console.log("not loadin: " + JSON.stringify(this.state.data));
		}

		return (
			<div className="main">
				<button onClick={this.progressTurn}>Progress Turn</button>
				<button onClick={this.showPlanetList}>Planet list</button>

				<ploxworld.Board data={this.state.data}></ploxworld.Board>

				<ploxworld.WorldStats className="WorldStats" data={this.state.data.worldData}></ploxworld.WorldStats>

				{ this.state.showPlanetList ? <ploxworld.PlanetDialog planets={this.state.data.planets}
																	  selectedPlanetName={this.state.selectedPlanetName}
																	  requestClose={this.closePlanetList}/> : null }
			</div>

		);
	}
});

ReactDOM.render(
	<Main startUrl="/backend" progressTurnUrl="/backend/progressTurn"/>,
	document.getElementById('content')
);
