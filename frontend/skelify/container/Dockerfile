FROM r-docker-registry-1-docker-io.artifactory.svc.elca.ch/nginx:1.29.1-alpine
ENV TZ Europe/Zurich

LABEL name="ephemeral/skelify-frontend" releaseName="release/skelify-frontend"

# Ensure image can run on OKD
RUN chgrp -R 0 /var/cache/nginx /var/run /var/log/nginx \
 && chmod -R g=u /var/cache/nginx /var/run /var/log/nginx

# Copy files
COPY dist/browser /usr/share/nginx/html/

WORKDIR /usr/share/nginx/html
USER nginx
