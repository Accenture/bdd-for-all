# Contributing

:+1::tada: First off, thanks for taking the time to contribute! :tada::+1:

The following is a set of guidelines for contributing to this project on GitHub. These are mostly guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

> Please note we have a code of conduct, please follow it in all your interactions with the project.

### Table of Contents

* [Code of Conduct](#code-of-conduct)
* [Reporting Bugs & Suggesting Enhancements](#reporting-bugs--suggesting-enhancements)
* [Pull Requests](#pull-requests)
  * [Code Quality & Style Guide](#code-quality--style-guide)
  * [Setting Up Your IDE](#setting-up-your-ide)
  * [Testing](#testing)
  * [Documentation](#documentation)
  * [Commit Messages](#commit-messages)
* [Attributions](#attributions)

### Code of Conduct

In the interest of fostering an open and welcoming environment, we as
contributors and maintainers pledge to making participation in our project and
our community a harassment-free experience for everyone, regardless of age, body
size, disability, ethnicity, gender identity and expression, level of experience,
nationality, personal appearance, race, religion, or sexual identity and
orientation.

#### Our Standards

Examples of behavior that contributes to creating a positive environment
include:

* Using welcoming and inclusive language
* Being respectful of differing viewpoints and experiences
* Gracefully accepting constructive criticism
* Focusing on what is best for the community
* Showing empathy towards other community members

Examples of unacceptable behavior by participants include:

* The use of sexualized language or imagery and unwelcome sexual attention or
advances
* Trolling, insulting/derogatory comments, and personal or political attacks
* Public or private harassment
* Publishing others' private information, such as a physical or electronic
  address, without explicit permission
* Other conduct which could reasonably be considered inappropriate in a
  professional setting

#### Our Responsibilities

Project maintainers are responsible for clarifying the standards of acceptable
behavior and are expected to take appropriate and fair corrective action in
response to any instances of unacceptable behavior.

Project maintainers have the right and responsibility to remove, edit, or
reject comments, commits, code, wiki edits, issues, and other contributions
that are not aligned to this Code of Conduct, or to ban temporarily or
permanently any contributor for other behaviors that they deem inappropriate,
threatening, offensive, or harmful.

#### Scope

This Code of Conduct applies both within project spaces and in public spaces
when an individual is representing the project or its community. Examples of
representing a project or community include using an official project e-mail
address, posting via an official social media account, or acting as an appointed
representative at an online or offline event. Representation of a project may be
further defined and clarified by project maintainers.

#### Enforcement

Instances of abusive, harassing, or otherwise unacceptable behavior may be
reported by contacting the project team. All
complaints will be reviewed and investigated and will result in a response that
is deemed necessary and appropriate to the circumstances. The project team is
obligated to maintain confidentiality with regard to the reporter of an incident.
Further details of specific enforcement policies may be posted separately.

Project maintainers who do not follow or enforce the Code of Conduct in good
faith may face temporary or permanent repercussions as determined by other
members of the project's leadership.

### Reporting Bugs & Suggesting Enhancements

Please submit all enhancement requests and bugs using [GitHub Issues](/issues), make sure to first check that it isn't a duplicate.

When submitting, please explain the request and include additional details to help maintainers reproduce the problem:

* Use a clear and descriptive title for the issue to identify the problem.
* Describe the exact steps which reproduce the problem in as many details as possible.
* Provide specific examples to demonstrate the steps. Include links to files or GitHub projects, or copy/pasteable snippets, which you use in those examples. If you're providing snippets in the issue, use Markdown code blocks.
* Describe the behavior you observed after following the steps and point out what exactly is the problem with that behavior.
* Explain which behavior you expected to see instead and why.
* If the problem is related to performance or memory, include a CPU profile capture with your report.
* If the problem wasn't triggered by a specific action, describe what you were doing before the problem happened and share more information using the guidelines below.

Provide more context by answering these questions:

* Did the problem start happening recently (e.g. after updating to a new version) or was this always a problem?
* If the problem started happening recently, can you reproduce the problem in an older version? What's the most recent version in which the problem doesn't happen? You can download older versions from the releases page.
* Can you reliably reproduce the issue? If not, provide details about how often the problem happens and under which conditions it normally happens.

Include details about your configuration and environment:

* Which version are you using?
* What's the name and version of the OS you're using?

### Pull Requests

The process described here has several goals:

* Maintain project quality
* Fix problems that are important to users
* Enable a sustainable system for project maintainers to review contributions

Please review the following subsections to ensure your pull request can be reviewed and merged.

> While the prerequisites below must be satisfied prior to having your pull request reviewed, the reviewer(s) may ask you to complete additional work, tests, or other changes before your pull request can be ultimately accepted.

#### Code Quality & Style Guide

We use checkstyle (Goolge's Style) for code formatting.  This is validated during maven builds, see [Setting Up Your IDE](#setting-up-your-ide) to make this easier.

For code quality we use spotbugs (including fb-contrib and findsecbugs), pmd and owasp dependency check for code analysis and security.  These are run during any build activity and should be clear of any warnings or errors.

You can see all the reports by running `mvn site` then opening up the homepage by going to target/sites/index.html and navigating to each of the reports.

> Occasionally we make exceptions, so if there's a rule that doesn't make sense ask and maybe...

#### Setting Up Your IDE 

Having the code analysis tools integrated your IDE reduces the likelihood of issues being discovered during the build.

##### IntelliJ

* First let's install the plugins...
  * Open `Preferences -> Plugins` and click `Browse Repositories`
  * In the search box enter `checkstyle`
  * Select `Checkstyle-IDEA` and click the `Install` button
  * Next in the search box enter `findbugs`
  * Select `Findbugs-IDEA` and click the `Install` button
  * Now restart your IDE.
* Now, let's configure checkstyle
  * Open `Preferences -> Other Settings` and click on `Checkstyle`
  * Under the "Configuration" section, click "Google Checks"
  * Then click `Apply` and `Ok`
* And finally findbugs..
  * Open `Preferences -> Other Settings` and click on `Findbugs`
  * In the "Plugins" section, click the `+` and choose `fb-contrib`
  * Now click the `+` again and choose `Find Security Bugs`.
  * The click `Apply` and `Ok`
  
##### Eclipse

Coming soon...

#### Testing

Tests are required for all contributions, most tests are cucumber based due to the nature of the library, but there are some exceptions.  Coverage should not drop below 90%.

> You can see all the reports by running `mvn site` then opening up the homepage by going to target/sites/index.html and navigating to each of the reports.

#### Documentation

When adding steps, modifying/adding features, etc... you need to update the documentation as well.  Whether it's the README or something that should be added to [docs/](docs/).

> If unsure where to put, please reach out to the project team.

#### Commit Messages

[How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)

### Attributions

This Code of Conduct is adapted from the [Contributor Covenant][cochomepage], version 1.4,
available at [http://contributor-covenant.org/version/1/4][cocversion]

Additional content adapted from the Atom Contributing guidelines [https://github.com/atom/atom/blob/master/CONTRIBUTING.md][atomcontrib]

[cochomepage]: http://contributor-covenant.org
[cocversion]: http://contributor-covenant.org/version/1/4/
[atomcontrib]: https://github.com/atom/atom/blob/master/CONTRIBUTING.md#pull-requests

   
 
 
