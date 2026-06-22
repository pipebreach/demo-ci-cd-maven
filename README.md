# Java Maven CI/CD Security Reference

This repository contains a small Spring Boot service and a layered GitHub
Actions setup for CI, build validation, and application security controls.

The service exposes a simple pricing quote calculation API so the repository can
demonstrate a complete pipeline on a **compiled language**:

- Maven build and unit tests
- format and static checks
- fast security scanning in CI
- SAST with CodeQL
- SCA with Dependency Review and scheduled dependency auditing
- preview environments with GitHub Codespaces
- post-deploy functional validation
- advisory DAST with OWASP ZAP

## Application overview

The API supports:

- `GET /` for service metadata
- `GET /health` for health checks
- `POST /api/v1/quotes` for discount quote calculations

The quote endpoint receives:

- `amount`
- `discountPercentage`
- `currency`

And returns:

- `originalAmount`
- `discountPercentage`
- `discountAmount`
- `finalAmount`
- `currency`

## Technology stack

- Java 21
- Spring Boot
- Maven Wrapper
- JUnit
- Spotless
- SpotBugs + FindSecBugs
- GitHub Actions
- GitHub CodeQL
- Dependabot / Dependency Review
- OWASP Dependency-Check
- OWASP ZAP

## Repository structure

```text
src/
├── main/java/com/pipebreach/democicdmaven/
│   ├── controller/
│   ├── model/
│   ├── service/
│   └── DemoCiCdMavenApplication.java
├── main/resources/
└── test/java/com/pipebreach/democicdmaven/

.devcontainer/
└── devcontainer.json

.github/
├── dependabot.yml
└── workflows/
```

## Local development

Run format, build, tests, and security checks:

```bash
./mvnw spotless:check
./mvnw -DskipTests package
./mvnw test
./mvnw spotbugs:check
```

Run the service locally:

```bash
./mvnw spring-boot:run
```

The application listens on:

```text
http://127.0.0.1:8000
```

## CI/CD workflows

### Pull request orchestration

`pr-validation.yml` is the entry point for pull requests. It orchestrates:

- `ci.yml`
- `sast.yml`
- `dependency-review.yml`
- `preview.yml` after the blocking checks succeed

The preview workflow then runs functional validation and DAST against the pull
request branch.

### CI

`ci.yml` is reusable and provides:

- `Lint & Format` via Spotless
- `Build` via Maven package
- `Security Scan` via SpotBugs and FindSecBugs
- `Unit Tests`
- `Merge Gate`

### SAST

`sast.yml` runs CodeQL for Java on pull requests, pushes to `main`, and on a
schedule.

### SCA

The SCA layer is split into two workflows:

- `dependency-review.yml` for pull request dependency changes
- `sca.yml` for scheduled and manual Maven dependency auditing

Dependabot configuration is included in `.github/dependabot.yml`.

> `Dependency Review` requires **Dependency graph** to be enabled in the
> repository security settings.

### Preview and post-deploy validation

`preview.yml` creates a short-lived GitHub Codespace on pushes to `main` and
can also be called from the pull request pipeline. It publishes a preview URL
and triggers:

- `functional-tests.yml`
- `dast.yml`

### DAST

`dast.yml` runs an OWASP ZAP full scan against the preview URL.

Policy:

- **High** findings fail the check
- **Medium**, **Low**, and **Informational** findings are reported in the
  workflow summary and artifact, but do not fail the check

## Required repository configuration

### Secrets

| Secret | Purpose |
|---|---|
| `CODESPACE_PAT` | Create, manage, and expose preview Codespaces |

### Repository settings

Enable the following in GitHub:

- **Actions**
- **Codespaces**
- **Dependency graph**
- **Code scanning / CodeQL**

If you are using `sast.yml`, disable GitHub's default CodeQL setup to avoid
duplicated code scanning runs.

Recommended branch protection for `main`:

- pull requests required
- required status checks
- direct pushes blocked

Suggested required checks:

- `CI / Lint & Format`
- `CI / Build`
- `CI / Security Scan`
- `CI / Unit Tests`
- `SAST / CodeQL Analysis (Java)`
- `SCA / Dependency Review`
- `Preview, Functional Tests, and DAST / DAST Scan / ZAP Baseline Scan`

## Design constraints

The implementation is intentionally compact:

- no database
- no authentication or authorization
- synchronous request/response flow
- no persistence layer

Those trade-offs keep the codebase small while still allowing meaningful build,
test, SAST, SCA, preview, and DAST validation.
