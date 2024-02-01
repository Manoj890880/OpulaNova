// $('#form').find('input, textarea').on('keyup blur focus', function (e) {

//     var $this = $(this),
//         label = $this.prev('label');

//     if (e.type === 'keyup') {
//         if ($this.val() === '') {
//             label.removeClass('active highlight');
//         } else {
//             label.addClass('active highlight');
//         }
//     } else if (e.type === 'blur') {
//         if ($this.val() === '') {
//             label.removeClass('active highlight');
//         } else {
//             label.removeClass('highlight');
//         }
//     } else if (e.type === 'focus') {

//         if ($this.val() === '') {
//             label.removeClass('highlight');
//         }
//         else if ($this.val() !== '') {
//             label.addClass('highlight');
//         }
//     }

// });

// $('.tab a').on('click', function (e) {

//     e.preventDefault();

//     $(this).parent().addClass('active');
//     $(this).parent().siblings().removeClass('active');

//     target = $(this).attr('href');

//     $('.tab-content > div').not(target).hide();

//     $(target).fadeIn(800);

// });


document.addEventListener("DOMContentLoaded", function () {
    var loginForm = document.getElementById("login-form");

    if (loginForm) {
        loginForm.addEventListener("submit", function (event) {
            event.preventDefault();

            var email = document.getElementById("loginemail").value;
            var password = document.getElementById("loginpassword").value;

            if (email === "admin@gmail.com" && password === "admin123") {
                // Redirect to admin.html in the "admin" folder
                window.location.href = "admincontrol/admin.html";
            } else {
                fetch("http://localhost:5454/auth/signin", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        email: email,
                        password: password
                    })
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    return response.json();
                })
                .then(data => {
                    // Check if the authentication was successful
                    if (data.jwt) {
                        // Store the JWT in localStorage
                        localStorage.setItem("jwt", data.jwt);
                        window.location.href = "usercontrol/products.html";
                    } else {
                        // Server response did not include a JWT
                        alert("Authentication failed. Incorrect username or password");
                    }
                })
                .catch(error => {
                    console.error("Error during authentication:", error);
                    // Handle other types of errors here
                    alert("An unexpected error occurred during authentication");
                });
            }
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    var signUpForm = document.getElementById("signup-form");

    if (signUpForm) {
        signUpForm.addEventListener("submit", function (event) {
            event.preventDefault();

            var firstName = document.getElementById("first_name").value;
            var lastName = document.getElementById("last_name").value;
            var email = document.getElementById("email").value;
            var phone = document.getElementById("phone").value;
            var password = document.getElementById("password").value;

            if (firstName && lastName && email && phone && password) {
                // Send OTP to the mobile number
                sendOTP(email, phone);
            } else {
                alert("Please fill in all required fields.");
            }
        });
    }
});

function sendOTP(username, phoneNumber) {
    // Prepare the request body
    var requestBody = {
        "username": username,
        "phoneNumber": "+91" + phoneNumber
    };

    // Make a POST request to send OTP
    fetch("http://localhost:5454/otp/send-otp", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        // Check if OTP sending was successful
        if (data.status==="DELIVERED") {
            
            // Prompt the user to enter OTP
            var enteredOTP = prompt("Enter the OTP sent to your mobile:");

            if (enteredOTP) {
                // Validate the entered OTP
                validateOTP(username, enteredOTP);
            } else {
                alert("OTP entry canceled.");
            }
        } else {
            alert("Error sending OTP. Please try again.");
        }
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

function validateOTP(username, otpNumber) {
    // Prepare the request body
    var requestBody = {
        username : username,
        otpNumber: otpNumber
    };

    // Make a POST request to validate OTP
    fetch("http://localhost:5454/otp/validate-otp", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
    })
    .then(response => response.text())
    .then(data => {
        // Check if OTP validation was successful
        console.log(data);
        if (data.trim() === "OTP is valid!") {
            // User validated successfully, proceed with signup
            signUpUser(username);
        } else {
            alert("Invalid OTP. Please try again.");
        }
    })
    .catch(error => {
        console.error("Error:", error);
    });
}

function signUpUser(username) {
    // Prepare the request body for user signup
    var requestBody = {
        "firstName": document.getElementById("first_name").value,
        "lastName": document.getElementById("last_name").value,
        "email": document.getElementById("email").value,
        "mobile": "+91" + document.getElementById("phone").value,
        "password": document.getElementById("password").value
    };

    // Make a POST request for user signup
    fetch("http://localhost:5454/auth/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
    })
    .then(response => response.json())
    .then(data => {
        // Check if signup was successful
        if (data.massage) {
            alert("User signed up successfully!");
            // Redirect to the products.html page
            window.location.href = "admincontrol/products/products.html";
        } else {
            alert("Error signing up user. Please try again.");
        }
    })
    .catch(error => {
        console.error("Error:", error);
    });
}
