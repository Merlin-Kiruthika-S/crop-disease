const API_BASE_URL = "http://localhost:8080/api";

let selectedRole = "FARMER";


/* =========================================================
   PAGE LOAD
========================================================= */

document.addEventListener("DOMContentLoaded", () => {

    loadRegions();

    updateRoleUI();

    setupLoginForm();

    setupRegisterForm();

});



/* =========================================================
   ROLE SELECTION
========================================================= */

function selectRole(role) {

    selectedRole = role;

    updateRoleUI();

    clearMessages();

}



function updateRoleUI() {

    const farmerButton =
        document.getElementById("farmerRoleBtn");

    const officerButton =
        document.getElementById("officerRoleBtn");


    if (farmerButton) {

        farmerButton.classList.toggle(
            "active",
            selectedRole === "FARMER"
        );

    }


    if (officerButton) {

        officerButton.classList.toggle(
            "active",
            selectedRole === "OFFICER"
        );

    }


    const loginTitle =
        document.getElementById("loginTitle");


    if (loginTitle) {

        loginTitle.textContent =
            selectedRole === "FARMER"
                ? "Sign in as Farmer"
                : "Sign in as Officer";

    }


    const registerTitle =
        document.getElementById("registerTitle");


    if (registerTitle) {

        registerTitle.textContent =
            selectedRole === "FARMER"
                ? "Create Farmer Account"
                : "Create Officer Account";

    }

}



/* =========================================================
   SHOW LOGIN
========================================================= */

function showLogin() {

    const loginSection =
        document.getElementById(
            "loginSection"
        );

    const registerSection =
        document.getElementById(
            "registerSection"
        );

    const loginTab =
        document.getElementById(
            "loginTab"
        );

    const registerTab =
        document.getElementById(
            "registerTab"
        );


    if (loginSection) {

        loginSection.classList.remove(
            "hidden"
        );

    }


    if (registerSection) {

        registerSection.classList.add(
            "hidden"
        );

    }


    if (loginTab) {

        loginTab.classList.add(
            "active"
        );

    }


    if (registerTab) {

        registerTab.classList.remove(
            "active"
        );

    }


    clearMessages();

}



/* =========================================================
   SHOW REGISTER
========================================================= */

function showRegister() {

    const loginSection =
        document.getElementById(
            "loginSection"
        );

    const registerSection =
        document.getElementById(
            "registerSection"
        );

    const loginTab =
        document.getElementById(
            "loginTab"
        );

    const registerTab =
        document.getElementById(
            "registerTab"
        );


    if (loginSection) {

        loginSection.classList.add(
            "hidden"
        );

    }


    if (registerSection) {

        registerSection.classList.remove(
            "hidden"
        );

    }


    if (loginTab) {

        loginTab.classList.remove(
            "active"
        );

    }


    if (registerTab) {

        registerTab.classList.add(
            "active"
        );

    }


    clearMessages();

}



/* =========================================================
   PASSWORD TOGGLE
========================================================= */

function togglePassword(
    inputId,
    button
) {

    const input =
        document.getElementById(
            inputId
        );


    if (!input) {
        return;
    }


    if (
        input.type ===
        "password"
    ) {

        input.type = "text";

        button.textContent = "🙈";

    } else {

        input.type = "password";

        button.textContent = "👁";

    }

}



/* =========================================================
   LOGIN
========================================================= */

function setupLoginForm() {

    const loginForm =
        document.getElementById(
            "loginForm"
        );


    if (!loginForm) {
        return;
    }


    loginForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const username =
                document
                    .getElementById(
                        "loginUsername"
                    )
                    .value
                    .trim();


            const password =
                document
                    .getElementById(
                        "loginPassword"
                    )
                    .value;


            if (
                !username ||
                !password
            ) {

                showLoginMessage(
                    "Please enter username and password.",
                    "error"
                );

                return;

            }


            const submitButton =
                loginForm.querySelector(
                    'button[type="submit"]'
                );


            if (submitButton) {

                submitButton.disabled =
                    true;

                submitButton
                    .querySelector("span")
                    .textContent =
                    "Signing in...";

            }


            try {

                const endpoint =
                    selectedRole === "OFFICER"
                        ? `${API_BASE_URL}/auth/officer/login`
                        : `${API_BASE_URL}/auth/farmer/login`;


                const response =
                    await fetch(
                        endpoint,
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify({

                                    username:
                                        username,

                                    password:
                                        password

                                })

                        }
                    );


                const result =
                    await response.json();


                if (!response.ok) {

                    showLoginMessage(
                        result.error ||
                        "Invalid username or password.",
                        "error"
                    );

                    return;

                }


                const user = {

                    id:
                        result.id,

                    name:
                        result.name,

                    username:
                        result.username,

                    role:
                        result.role ||
                        selectedRole

                };


                localStorage.setItem(
                    "cropAdvisorUser",
                    JSON.stringify(user)
                );


                showLoginMessage(
                    "Login successful. Opening dashboard...",
                    "success"
                );


                setTimeout(() => {

                    window.location.href =
                        "dashboard.html";

                }, 500);


            } catch (error) {

                console.error(
                    "Login error:",
                    error
                );


                showLoginMessage(
                    "Unable to connect to CropAdvisor backend.",
                    "error"
                );

            } finally {

                if (submitButton) {

                    submitButton.disabled =
                        false;

                    submitButton
                        .querySelector("span")
                        .textContent =
                        "Sign In";

                }

            }

        }
    );

}



/* =========================================================
   REGISTER
========================================================= */

function setupRegisterForm() {

    const registerForm =
        document.getElementById(
            "registerForm"
        );


    if (!registerForm) {
        return;
    }


    registerForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const name =
                document
                    .getElementById(
                        "registerName"
                    )
                    .value
                    .trim();


            const phone =
                document
                    .getElementById(
                        "registerPhone"
                    )
                    .value
                    .trim();


            const email =
                document
                    .getElementById(
                        "registerEmail"
                    )
                    .value
                    .trim();


            const username =
                document
                    .getElementById(
                        "registerUsername"
                    )
                    .value
                    .trim();


            const password =
                document
                    .getElementById(
                        "registerPassword"
                    )
                    .value;


            const regionId =
                Number(
                    document
                        .getElementById(
                            "registerRegion"
                        )
                        .value
                );


            if (
                !name ||
                !phone ||
                !email ||
                !username ||
                !password
            ) {

                showRegisterMessage(
                    "Please fill in all required fields.",
                    "error"
                );

                return;

            }


            if (
                !regionId ||
                Number.isNaN(regionId)
            ) {

                showRegisterMessage(
                    "Please select a region.",
                    "error"
                );

                return;

            }


            const submitButton =
                registerForm.querySelector(
                    'button[type="submit"]'
                );


            if (submitButton) {

                submitButton.disabled =
                    true;

                submitButton
                    .querySelector("span")
                    .textContent =
                    "Creating...";

            }


            try {

                const endpoint =
                    selectedRole === "OFFICER"
                        ? `${API_BASE_URL}/auth/officer/register`
                        : `${API_BASE_URL}/auth/farmer/register`;


                const accountData = {

                    name:
                        name,

                    phone:
                        phone,

                    email:
                        email,

                    username:
                        username,

                    password:
                        password,

                    region: {

                        id:
                            regionId

                    }

                };


                const response =
                    await fetch(
                        endpoint,
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    accountData
                                )

                        }
                    );


                const result =
                    await response.json();


                if (!response.ok) {

                    showRegisterMessage(
                        result.error ||
                        "Unable to create account.",
                        "error"
                    );

                    return;

                }


                showRegisterMessage(
                    "Account created successfully! Please sign in.",
                    "success"
                );


                registerForm.reset();


                setTimeout(() => {

                    showLogin();

                }, 1000);


            } catch (error) {

                console.error(
                    "Registration error:",
                    error
                );


                showRegisterMessage(
                    "Unable to connect to CropAdvisor backend.",
                    "error"
                );

            } finally {

                if (submitButton) {

                    submitButton.disabled =
                        false;

                    submitButton
                        .querySelector("span")
                        .textContent =
                        "Create Account";

                }

            }

        }
    );

}



/* =========================================================
   LOAD REGIONS
========================================================= */

async function loadRegions() {

    const regionSelect =
        document.getElementById(
            "registerRegion"
        );


    if (!regionSelect) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/regions`
            );


        if (!response.ok) {

            throw new Error(
                "Unable to load regions"
            );

        }


        const regions =
            await response.json();


        regionSelect.innerHTML = `
            <option value="">
                Select region
            </option>
        `;


        regions.forEach(region => {

            const option =
                document.createElement(
                    "option"
                );


            option.value =
                region.id;


            option.textContent =
                region.name;


            regionSelect.appendChild(
                option
            );

        });


    } catch (error) {

        console.error(
            "Region loading error:",
            error
        );

    }

}



/* =========================================================
   LOGIN MESSAGE
========================================================= */

function showLoginMessage(
    message,
    type
) {

    const element =
        document.getElementById(
            "loginMessage"
        );


    if (!element) {
        return;
    }


    element.textContent =
        message;


    element.className =
        "form-message " +
        type;


    element.style.display =
        "block";

}



/* =========================================================
   REGISTER MESSAGE
========================================================= */

function showRegisterMessage(
    message,
    type
) {

    const element =
        document.getElementById(
            "registerMessage"
        );


    if (!element) {
        return;
    }


    element.textContent =
        message;


    element.className =
        "form-message " +
        type;


    element.style.display =
        "block";

}



/* =========================================================
   CLEAR MESSAGES
========================================================= */

function clearMessages() {

    const loginMessage =
        document.getElementById(
            "loginMessage"
        );


    const registerMessage =
        document.getElementById(
            "registerMessage"
        );


    if (loginMessage) {

        loginMessage.textContent =
            "";

        loginMessage.style.display =
            "none";

    }


    if (registerMessage) {

        registerMessage.textContent =
            "";

        registerMessage.style.display =
            "none";

    }

}