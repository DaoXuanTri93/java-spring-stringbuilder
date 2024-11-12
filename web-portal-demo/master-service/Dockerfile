# Chọn image nền
FROM openjdk:11-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file jar vào container
COPY target/master-service-1.0-SNAPSHOT.jar master-service.jar

# Chạy ứng dụng
CMD ["java", "-jar", "master-service.jar"]
