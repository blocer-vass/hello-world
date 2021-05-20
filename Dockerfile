FROM nginx:alpine
COPY hello-world/target/dist/ /usr/share/nginx/html
EXPOSE 80
