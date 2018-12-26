ps -ef |grep daily_task |awk '{print $2}'|xargs kill -9
nohup sh /web/daily/daily-task/start.sh & 
rm -rf /web/daily/daily-task/nohup.out
