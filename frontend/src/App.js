import React from 'react'
import {BrowserRouter as Router, Link, Redirect, Route, Switch} from "react-router-dom";
import {Container, Menu,} from 'semantic-ui-react'
import Channels from "./channels/Channels";
import NotFound from "./NotFound";
import Home from "./Home";

const FixedMenuLayout = () => (
    <div>
        <Router basename="gui">
            <Menu fixed='top' inverted>
                <Container>
                    <Menu.Item as='a' header>
                        IPTV Scanner
                    </Menu.Item>
                    <Menu.Item><Link to="/channels">Channels</Link></Menu.Item>
                </Container>
            </Menu>

            <Switch>
                <Route exact="" path="/">
                    <Home/>
                </Route>
                <Route path="/channels">
                    <Channels/>
                </Route>
                <Route path="/404">
                    <NotFound/>
                </Route>
                <Redirect to="/404"/>
            </Switch>
        </Router>
    </div>
)

export default FixedMenuLayout