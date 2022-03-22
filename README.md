# JWT Deep Link Generator

Generator of deep links with JWT token parameters. The purpose of this application is to allow simple testing of deep-linked activations with signed JWT as a deep link parameter.

## Configuration

Application supports various Spring Boot properties. Besides that, you can configure the following properties:

```
# Prefix for the username in UI, useful in case usernames have fixed prefixes.
powerauth.jwt.config.username-prefix=System:

# Prefix for the deeplink. The JWT value is appended after this prefix.
powerauth.jwt.config.deeplink-prefix=myscheme://activation?jwt=

# Default JWT expiration in seconds.
powerauth.jwt.config.expiration=300

# JWT signing algorithm. Only "RS" and "ES" algorithms are supported.
powerauth.jwt.algorithm=ES512

# Private key used for JWT signatures. The value below is an example value from https://jwt.io/.
# Replace it with your private key (PEM without header/footer).
powerauth.jwt.private-key=MIHuAgEAMBAGByqGSM49AgEGBSuBBAAjBIHWMIHTAgEBBEIBiyAa7aRHFDCh2qga\
  9sTUGINE5jHAFnmM8xWeT/uni5I4tNqhV5Xx0pDrmCV9mbroFtfEa0XVfKuMAxxf\
  Z6LM/yKhgYkDgYYABAGBzgdnP798FsLuWYTDDQA7c0r3BVk8NnRUSexpQUsRilPN\
  v3SchO0lRw9Ru86x1khnVDx+duq4BiDFcvlSAcyjLACJvjvoyTLJiA+TQFdmrear\
  jMiZNE25pT2yWP1NUndJxPcvVtfBW48kPOmvkY4WlqP5bAwCXwbsKrCgk6xbsp12\
  ew==
```

## Getting Started

Start the Spring Boot application using:

```sh
java -jar jwt-generator.war
```

You can download the WAR file in the [releases](https://github.com/wultra/jwt-generator/releases) section, or you can clone the source codes and build the WAR file yourself:

```sh
git clone https://github.com/wultra/jwt-generator.git
cd jwt-generator
mvn clean package -DskipTests=true
```

Alternatively, clone the source codes and run the application using Maven:

```sh
git clone https://github.com/wultra/jwt-generator.git
cd jwt-generator
mvn spring-boot:run
```
