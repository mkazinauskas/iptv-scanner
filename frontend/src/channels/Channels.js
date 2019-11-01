import React from 'react';
import {
    Container,
    Header,
    Button
} from 'semantic-ui-react';

import ChannelsTable from './Table'

class Channels extends React.Component {
    render() {
        return (
            <Container style={{ marginTop: '7em' }}>
                <Header as='h1'>Current channels</Header>
                <div style={{ marginBottom: '0.5em' }}>
                    <Button class="ui button" href="http://localhost:8080/channels/export?size=1000&status=WORKING" target="_blank">Download Working channels</Button>
                    <Button class="ui button" href="http://localhost:8080/channels/export?size=1000" target="_blank">Download All channels</Button>
                </div>
                <div><ChannelsTable /></div>
            </Container >
        );
    }
}

export default Channels;