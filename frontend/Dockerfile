FROM node:20.12.2-alpine3.19

WORKDIR /usr/src/app

COPY --chown=root:root --chmod=755 ["package.json", "yarn.lock", "/usr/src/app/"]
RUN yarn install --ignore-scripts

COPY --chown=root:root --chmod=755 . /usr/src/app

EXPOSE 9000