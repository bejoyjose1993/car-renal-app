# Build Angular app
FROM node:18-alpine AS build

ARG BASE_API_URL
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# Inject BASE_API_URL into environment file (optional approach below)
RUN echo "export const environment = { production: true, apiUrl: '${BASE_API_URL}' };" > src/environments/environment.prod.ts
RUN npx ng build --configuration production --no-progress

# Serve with Nginx
FROM nginx:alpine
# Install envsubst
RUN apk add --no-cache gettext

COPY --from=build /app/dist/car_rental_angular /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Default port
ENV FRONTEND_PORT=80

# Use envsubst to replace variables in config
CMD envsubst '$FRONTEND_PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf \
    && nginx -g 'daemon off;'


#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]