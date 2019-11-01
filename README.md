# UDP IPTV streams scanner

## How to run in Docker (Not working yet...)
WARNING: Known issue... Scanning will fail with error: vlcpulse audio output error: 
`PulseAudio server connection failure: Connection refused`

1. `./gradlew build`
2. `docker build . -t=iptv-scanner`
3. `docker-compose up`
4. Open browser: `http://localhost:8181`

## How to run using your system 
1. Make sure, that vlc is installed
2. `docker-compose up iptv-scanner-database`
3. `./gradlew bootRun`
4. Open browser: `http://localhost:8080`