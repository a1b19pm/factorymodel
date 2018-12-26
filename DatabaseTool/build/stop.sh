ps -ef |grep daily_task |awk '{print $2}'|xargs kill -9
