![GitHub language count](https://img.shields.io/github/languages/count/ClaudiaAF/UPCourse?colorB=ed960b)
![GitHub repo size](https://img.shields.io/github/repo-size/ClaudiaAF/UPCourse?colorB=ed960b)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/ClaudiaAF/UPCourse?colorB=ed960b)
![GitHub watchers](https://img.shields.io/github/watchers/ClaudiaAF/UPCourse?colorB=ed960b)



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/claudiaAF/UPCourse">
    <img src="https://user-images.githubusercontent.com/64257497/131014108-4e2cdf5f-ae35-4c20-9c57-51c93886add8.png "width="195" alt="logo" >

  </a>

  <h3 align="center">UPCourse</h3>

  <p align="center">
    University Administration Portal
    <br />
    <a href="https://github.com/claudiaAF/UPCourse"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/claudiaAF/UPCourse">View Demo</a>
    ·
    <a href="https://github.com/claudiaAF/UPCourse/issues">Report Bug</a>
    ·
    <a href="https://github.com/claudiaAF/UPCourse/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
<summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#getting-started">Getting Started</a>
    <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
      <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#features-and-functionality">Features and Functionality</a>
    </li>
    <li><a href="#concept-process">Concept Process</a>
    <ul>
        <li><a href="#ideation">Ideation</a></li>
        <li><a href="#wireframes">Wireframes</a></li>
      <li><a href="#sqlite-database">SQLite Database</a></li>
      </ul>
    </li>
    <li><a href="#development-process">Development Process</a>
    <ul>
      <li><a href="#implementation">Implementation</a>
        <ul>
        <li><a href="#highlights">Highlights</a></li>
        <li><a href="#challenges">Challenges</a></li>
        </ul>
      </li>
      <li><a href="#future-implementation">Future Implementation</a></li>
      </ul>
    </li>
    <li>
      <a href="#final-outcome">Final Outcome</a>
      <ul>
        <li><a href="#mockups">Mockups</a></li>
        <li><a href="#video-demonstration">Video Demonstration</a></li>
      </ul>
    </li>    
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>





<!-- ABOUT THE PROJECT -->
## About The Project


![header](https://user-images.githubusercontent.com/64257497/131015410-44ec3e46-5948-4d4c-81ce-01332f4fb382.png)


UPCourse is a simple and intuitive university admin application, made for updating and keeping track of your university administration in one space. 

### Built With
<img src="https://user-images.githubusercontent.com/64257497/141121535-00e814cc-4af3-46e0-a7dc-bbcb24068a6c.png" width="5%" height="5%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/64257497/141121541-cff74ba9-50dc-4159-8b98-c1d49fb9ecda.png" width="10%" height="10%">

## Getting Started
### Prerequisites
* The latest version of <a href="https://www.jetbrains.com/idea/download/#section=windows">`IntelliJ IDEA`</a> installed on your machine (v2021.2.3)
* The Kotlin plugin installed in `IntelliJ`

### Installation
* Clone the repository

```sh
git clone https:://github.com/ClaudiaAF/UPCourse.git
```

<!-- USAGE EXAMPLES -->
## Features and Functionality
### Features
Features of this application include:

<img width="1920" alt="Slide 16_9 - 16" src="https://user-images.githubusercontent.com/64257497/131016726-5a9f55ec-5f08-4bfa-9705-a2be3ad360b7.png">

* A welcome page introducing the user to the application.

<img width="1920" alt="Slide 16_9 - 17" src="https://user-images.githubusercontent.com/64257497/131016736-eddddf0e-f1d6-49c6-902b-fbc0dfa8a5bb.png">

* Create collections of students, lecturers, administrative staff and subjects that are stored in a backing SQlite database
* Add new people and create new subjects
* Remove people and / or subjects
* Update people and subject information

<img width="1920" alt="Slide 16_9 - 18" src="https://user-images.githubusercontent.com/64257497/131016744-3fdfa948-ca2b-4579-93cb-f14b17780579.png">

* Assign subjects to students and lecturers

<img width="1920" alt="Slide 16_9 - 19" src="https://user-images.githubusercontent.com/64257497/131016751-cc43fc16-46f1-47ac-a4ef-a0ae2c9b4048.png">

* View the universities funds pool, and settle the in and outgoing payments

### Functionality

* Project link with `SQLite` allows for storage of collections and tables in the database. Values and items can be retrieved and displayed through transaction functions to the database.
* The ability to create new subjects and add people to the database through a simple `add function` by inserting the new values. 
* By selecting all values in certain tables and storing those in a `var`, which is then initialised, allows for the back-end data to be viewed and identified with ease.
* Similar to the add function, an update function is created in order to locate the `field(s)` being edited, and then an `execute function` is run to update the values. 
* Through identifying the item that has been selected in the table, this item is then identified in the `data model` as well, and the `delete function` is used to remove the item.

## Concept Process
### Ideation
![Group 5372](https://user-images.githubusercontent.com/64257497/141126836-bd3a96a8-9fab-4283-952f-267efbe545b1.png)

### Wireframes
![Group 5374](https://user-images.githubusercontent.com/64257497/141127164-78fe68d5-e41c-44bf-9874-360ba9ddde6d.png)

### SQlite Database

<img width="1897" alt="Group 56" src="https://user-images.githubusercontent.com/64257497/131015269-8c89a923-b09b-4775-8a3f-18107108207f.png">

## Development Process
The technical implementations and functionality done in the frontend and backend of the application.
### Implementation 
#### Highlights
* Implementing an SQLite database with accompanying CRUD functionality.
* Working with Kotlin and making use of Object Oriented Programming techniques.

#### Challenges
* Implementing the design in TornadoFX due to the lack of documentation on the framework.
* Making the Kotlin logic communicate with the TornadoFX interface.

### Future Implementation
* Make the interative components more dynamic with regards to the database.
* Apply more good coding practice in general to the project.
* Look into making the navigation look and operate a bit better.

## Final Outcome
### Mockups
![Group 1](https://user-images.githubusercontent.com/64257497/141128935-5c569a5f-c445-48d7-ab81-1e045609ee9d.png)


### Video Demonstration
Click <a href="https://drive.google.com/file/d/1zqNy9HiA9map0vN3gOAigmY_-DJ39H39/view?usp=sharing">here</a> to view the video demonstration!


<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/github_username/repo_name/issues) for a list of proposed features (and known issues).


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

* **Claudia Ferreira** - 180181@virtualwindow.co.za
* **Project Link** - https://github.com/ClaudiaAF/UPCourse.git



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [TornadoFX Guide](https://edvin.gitbooks.io/tornadofx-guide/content/)
* Lecturer Christof Enslin
* [Plagiarism Form](PlagiarismForm_ClaudiaFerreira_180181.png)



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=d0ac4b
[linkedin-url]: https://linkedin.com/in/ClaudiaAF
