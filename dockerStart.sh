docker ps>>dockerStart.txt

#docker run -d --name test_chrome -p 4444:4444 -v Data/.:/home/seluser/Downloads selenium/standalone-chrome:latest>dockerStart.txt
docker run -d --name test_chrome -p 4444:4444 -e SCREEN_WIDTH=1367 -e SCREEN_HEIGHT=768 -e SCREEN_DEPTH=24 -v Data/.:/home/seluser/Downloads selenium/standalone-chrome:3.141.59-zinc>dockerStart.txt
docker cp Data/. test_chrome:/home/seluser/Downloads

#docker network create grid
#docker run -d -p 4444:4444 --net grid --name test_chrome selenium/hub:3.11.0-dysprosium
#docker run -d --net grid -e HUB_HOST=test_chrome -p 4445:4444 --name p_4445 -v Data/.:/home/seluser/Downloads selenium/node-chrome:3.11.0-dysprosium
#docker run -d --net grid -e HUB_HOST=test_chrome -p 4446:4444 --name p_4446 -v Data/.:/home/seluser/Downloads selenium/node-chrome:3.11.0-dysprosium
#docker cp Data/. p_4445:/home/seluser/Downloads
#docker cp Data/. p_4446:/home/seluser/Downloads

exit