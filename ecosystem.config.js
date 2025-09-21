module.exports = {
  apps: [{
    name: 'mindustry-server',
    script: 'java',
    args: '-Xmx512M -jar server.jar',
    cwd: './server',
    instances: 1,
    exec_mode: 'fork',
    max_memory_restart: '512M',
    env: {
      NODE_ENV: 'production'
    },
    out_file: './logs/mindustry-out.log',
    error_file: './logs/mindustry-error.log',
    combine_logs: true,
    log_date_format: 'YYYY-MM-DD HH:mm:ss',
    min_uptime: '5s',
    max_restarts: 10,
    restart_delay: 5000
  }]
};