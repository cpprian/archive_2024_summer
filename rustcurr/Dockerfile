FROM ubuntu@sha256:69ce9399fe60c5710baee8416b7991183653c3d577afc2b0e3bfe508d7c76142
RUN apt-get update && \
    apt-get -y install ca-certificates libssl-dev && \
    rm -rf /var/lib/apt/lists/*

FROM rust:1.76 as build

WORKDIR /app
COPY . .

RUN cargo build --release

# To only run cli app, this lines are comment, because of testing purpose 
# FROM ubuntu@sha256:69ce9399fe60c5710baee8416b7991183653c3d577afc2b0e3bfe508d7c76142

# COPY --from=build /app/target/release/rustcurr /usr/local/bin/rustcurr