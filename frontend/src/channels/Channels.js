import React from 'react';
import {
    Container,
    Header,
} from 'semantic-ui-react';

import ChannelsTable from './Table'

class Channels extends React.Component {
    render() {
        return (
            <Container style={{ marginTop: '7em' }}>
                <Header as='h1'>Current channels</Header>
                <div><ChannelsTable /></div>
            </Container >
        );
    }
}

export default Channels;