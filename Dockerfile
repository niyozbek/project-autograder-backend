FROM gradle:8.5-jdk17

WORKDIR /app
CMD ["gradle", "bootRun", "--no-daemon"]