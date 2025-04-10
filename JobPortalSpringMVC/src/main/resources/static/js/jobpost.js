document.querySelectorAll('.example-box').forEach(box => {
    box.addEventListener('click', function () {
        document.querySelector('.custom-input').value = this.textContent;
    });
});

let currentStep = 1;
const totalSteps = 5;
const progressBar = document.getElementById('progressBar');
const nextBtn = document.getElementById('nextBtn');
const backBtn = document.getElementById('backBtn');
const continueWithoutBudgetBtn = document.querySelector('.btn-secondary');
function updateStep() {
    document.querySelectorAll('.container-box').forEach((step, index) => {
        const stepNumber = index + 1;

        console.log(`Updating Step: ${stepNumber}, Current Step: ${currentStep}`); // Debugging log

        if (stepNumber === currentStep) {
            step.classList.remove('hidden', 'fade-out', 'slide-left', 'slide-right');
            step.classList.add('fade-in'); // ✅ Smooth fade-in for active step
        } else {
            step.classList.remove('fade-in');
            step.classList.add('hidden'); // ✅ Hide inactive steps
        }
    });

    // ✅ Smooth progress bar update
    progressBar.style.transition = "width 0.5s ease";
    progressBar.style.width = `${(currentStep / totalSteps) * 100}%`;
}


// ✅ Next Button Click (Only Slide Out Current Step)
nextBtn.addEventListener('click', function () {
    if (currentStep < totalSteps) {
        let currentForm = document.getElementById(`step${currentStep}`);
        currentForm.classList.remove('fade-in', 'slide-right');
        currentForm.classList.add('slide-left'); // ✅ Smooth slide left

        setTimeout(() => {
            currentForm.classList.add('hidden'); // ✅ Hide after animation completes
            currentStep++;
            updateStep();
        }, 300);
    }
});

// ✅ Back Button Click (Only Slide Right)
backBtn.addEventListener('click', function () {
    if (currentStep > 1) {
        let currentForm = document.getElementById(`step${currentStep}`);
        currentForm.classList.remove('fade-in', 'slide-left');
        currentForm.classList.add('slide-right'); // ✅ Smooth slide right

        setTimeout(() => {
            currentForm.classList.add('hidden');
            currentStep--;
            updateStep();
        }, 300);
    }
});

// ✅ Move to Step 5 when clicking "Continue Without Budget"
continueWithoutBudgetBtn.addEventListener('click', function () {
    if (currentStep === 4) {
        closeModal();
        let currentForm = document.getElementById(`step${currentStep}`);
        currentForm.classList.remove('fade-in', 'slide-right');
        currentForm.classList.add('slide-left');

        setTimeout(() => {
            currentForm.classList.add('hidden');
            currentStep = 5;
            updateStep();
        }, 300);
    }
});

// ✅ Initialize Step 1 on Load
updateStep();


function selectBudgetType(type) {
    document.querySelectorAll('.budget-option').forEach(option => {
        option.classList.remove('selected');
        option.querySelector('input').checked = false;
    });

    let selectedOption = document.querySelector(`[name=budget][value=${type}]`);
    selectedOption.checked = true;
    selectedOption.parentElement.classList.add('selected');

    // Toggle form visibility based on selection
    if (type === 'fixed') {
        document.getElementById('fixedPriceForm').style.display = 'block';
        document.getElementById('hourlyPriceForm').style.display = 'none';
    } else {
        document.getElementById('fixedPriceForm').style.display = 'none';
        document.getElementById('hourlyPriceForm').style.display = 'flex';
    }
}
function showModal() {
    let modal = document.getElementById('budgetModal');
    let overlay = document.getElementById('modalOverlay');

    overlay.style.display = 'block';  // ✅ Show overlay
    modal.style.display = 'block';  // ✅ Show modal
    setTimeout(() => {
        modal.classList.add('show');
    }, 50);
}

function closeModal() {
    let modal = document.getElementById('budgetModal');
    let overlay = document.getElementById('modalOverlay');

    modal.classList.remove('show');
    setTimeout(() => {
        modal.style.display = 'none';  // ✅ Hide modal
        overlay.style.display = 'none'; // ✅ Hide overlay
    }, 300);
}

// Handle skipping budget
function skipBudget() {
    showModal();
}

// Continue without budget
function continueWithoutBudget() {
    closeModal();
    alert("Moving to the next step...");
}

document.querySelectorAll('.skill-badge').forEach(skill => {
    skill.addEventListener('click', function () {
        const skillText = this.textContent.trim().replace('+', '');

        if (!document.querySelector(`[data-skill='${skillText}']`)) {
            const selectedSkillsContainer = document.getElementById('selectedSkills');
            const skillElement = document.createElement('span');
            skillElement.classList.add('selected-skill');
            skillElement.setAttribute('data-skill', skillText);
            skillElement.innerHTML = `${skillText} <i class='fas fa-times remove-skill'></i>`;
            selectedSkillsContainer.appendChild(skillElement);

            // ✅ Hide the selected skill from popular skills
            this.style.display = 'none';

            document.getElementById('selectedSkillsHeading').classList.remove('hidden');
        }
    });
});

// ✅ Handle Skill Removal (Deselecting)
document.getElementById('selectedSkills').addEventListener('click', function (event) {
    if (event.target.classList.contains('remove-skill')) {
        const skillElement = event.target.parentElement;
        const skillText = skillElement.getAttribute('data-skill');

        // ✅ Show the skill back in "Popular Skills"
        document.querySelectorAll('.skill-badge').forEach(skill => {
            if (skill.textContent.trim().replace('+', '') === skillText) {
                skill.style.display = 'inline-block';
            }
        });

        // ✅ Remove from "Selected Skills"
        skillElement.remove();

        // ✅ Hide heading if no skills are selected
        if (!this.children.length) {
            document.getElementById('selectedSkillsHeading').classList.add('hidden');
        }
    }
});

// ✅ Add plus icon to popular skills
document.querySelectorAll('.skill-badge').forEach(skill => {
    skill.innerHTML = `<i class="fas fa-plus"></i> ${skill.textContent}`;
});
document.addEventListener("DOMContentLoaded", function () {
    let optionGroups = document.querySelectorAll(".option-group");

    // ✅ Hide all sections except the first one
    optionGroups.forEach((section, index) => {
        section.style.display = index === 0 ? "flex" : "none";
    });

    // ✅ Reference elements
    let locationInputContainer = document.getElementById("jobLocationSection");
    let locationContainer = document.getElementById("jobLocationContainer");
    let addLocationBtn = document.getElementById("addLocationBtn");
    let saveLocationBtn = document.getElementById("saveLocationBtn");
    let educationSection = document.getElementById("educationSection");
    let educationContainer = document.getElementById("educationContainer");
    let addEducationBtn = document.getElementById("addEducationBtn");
    let saveEducationBtn = document.getElementById("saveEducationBtn");
    let skipLocation = false;

    // ✅ Hide elements initially
    if (locationInputContainer) locationInputContainer.style.display = "none";
    if (educationSection) educationSection.style.display = "none";
    if (educationContainer) educationContainer.style.display = "none";
    if (addEducationBtn) addEducationBtn.style.display = "none";
    if (saveEducationBtn) saveEducationBtn.style.display = "none";
    if (addLocationBtn) addLocationBtn.style.display = "none";
    if (saveLocationBtn) saveLocationBtn.style.display = "none";

    // ✅ Handle option selection and transitions
    optionGroups.forEach((group, index) => {
        group.querySelectorAll(".option").forEach(option => {
            option.addEventListener("click", function () {
                let input = this.querySelector("input");
                if (input) {
                    input.checked = true;

                    document.querySelectorAll(`input[name="${input.name}"]`).forEach(radio => {
                        radio.parentElement.classList.remove("selected");
                    });

                    this.classList.add("selected");

                    if (group.id === "workModeSection") {
                        skipLocation = input.value.toLowerCase() === "online";
                    }

                    if (group.id === "experienceSection") {
                        setTimeout(() => {
                            group.style.display = "none";
                            skipLocation ? showEducationSection() : showJobLocationSection();
                        }, 300);
                        return;
                    }

                    setTimeout(() => {
                        group.style.display = "none";
                        let nextGroup = optionGroups[index + 1];
                        if (nextGroup) {
                            nextGroup.style.display = "flex";
                        }
                    }, 300);
                }
            });
        });
    });

    // ✅ Function to Show Job Location Section
    function showJobLocationSection() {
        locationInputContainer.style.display = "flex";
        addLocationBtn.style.display = "inline-block";
        saveLocationBtn.style.display = "inline-block";

        if (locationContainer.children.length === 0) {
            addLocationField();
        }
    }

    // ✅ Handle Save Button for Job Location
    if (saveLocationBtn) {
        saveLocationBtn.addEventListener("click", function () {
            hideJobLocationSection();
            showEducationSection();
        });
    }

    // ✅ Handle Adding & Removing Job Location Fields
    if (addLocationBtn) {
        addLocationBtn.addEventListener("click", function () {
            addLocationField();
        });
    }

    function hideJobLocationSection() {
        locationInputContainer.style.display = "none";
        addLocationBtn.style.display = "none";
        saveLocationBtn.style.display = "none";
    }

    function addLocationField() {
        let newField = document.createElement("div");
        newField.classList.add("location-input");
        newField.innerHTML = `
            <input type="text" class="custom-input" placeholder="Enter Job Location">
            <button type="button" class="remove-btn">🗑️</button>
        `;

        locationContainer.appendChild(newField);
        locationContainer.style.display = "block";
        newField.querySelector("input").focus();

        newField.querySelector(".remove-btn").addEventListener("click", function () {
            removeLocationField(newField);
        });
    }

    function removeLocationField(field) {
        field.remove();
        if (locationContainer.children.length === 0) {
            addLocationField();
        }
    }

    if (saveEducationBtn) {
        saveEducationBtn.addEventListener("click", function () {
            hideEducationSection();
        });
    }

    if (addEducationBtn) {
        addEducationBtn.addEventListener("click", function () {
            addEducationField();
        });
    }

    function showEducationSection() {
        educationSection.style.display = "flex";
        educationContainer.style.display = "block";
        addEducationBtn.style.display = "inline-block";
        saveEducationBtn.style.display = "inline-block";

        if (educationContainer.children.length === 0) {
            addEducationField();
        }
    }

    function hideEducationSection() {
        educationSection.style.display = "none";
        addEducationBtn.style.display = "none";
        saveEducationBtn.style.display = "none";
    }

    function addEducationField() {
        let newField = document.createElement("div");
        newField.classList.add("edu-input");
        newField.innerHTML = `
            <input type="text" class="custom-input" placeholder="Enter qualification (e.g., B.Sc in Mathematics)">
            <button type="button" class="remove-btn">🗑️</button>
        `;

        educationContainer.appendChild(newField);
        educationContainer.style.display = "block";
        newField.querySelector("input").focus();

        newField.querySelector(".remove-btn").addEventListener("click", function () {
            removeEducationField(newField);
        });
    }

    function removeEducationField(field) {
        field.remove();
        if (educationContainer.children.length === 0) {
            addEducationField();
        }
    }

    document.querySelectorAll(".edit-btn").forEach(button => {
        button.addEventListener("click", function () {
            let sectionId = this.getAttribute("data-target");
            reopenSection(sectionId);
        });
    });
});

function reopenSection(sectionId) {
    console.log("Opening section:", sectionId); // Debugging log

    let section = document.getElementById(sectionId);

    if (!section) {
        console.error("Section not found:", sectionId);
        return; // Exit if section is not found
    }

    // Hide all sections
    let allSections = document.querySelectorAll(".option-group, #jobLocationSection, #educationSection");
    allSections.forEach(sec => sec.style.display = "none");

    // Show the selected section
    section.style.display = "flex";

    // Special handling for Job Location
    if (sectionId === "jobLocationSection") {
        document.getElementById("jobLocationContainer").style.display = "block";
        document.getElementById("addLocationBtn").style.display = "inline-block";
        document.getElementById("saveLocationBtn").style.display = "inline-block";
    }

    // Special handling for Education Section
    if (sectionId === "educationSection") {
        document.getElementById("educationContainer").style.display = "block";
        document.getElementById("addEducationBtn").style.display = "inline-block";
        document.getElementById("saveEducationBtn").style.display = "inline-block";
    }
}

// ✅ Handle budget selection separately
document.querySelectorAll(".budget-group .option").forEach(option => {
    option.addEventListener("click", function () {
        let input = this.querySelector("input");
        if (input) {
            input.checked = true;

            // Remove 'selected' class from other budget options
            document.querySelectorAll(".budget-group .option").forEach(opt => {
                opt.classList.remove("selected");
            });

            // Add 'selected' class to the clicked option
            this.classList.add("selected");
        }
    });
});

document.querySelector('form').addEventListener('submit', function (e) {
    const selectedSkills = Array.from(document.querySelectorAll('.selected-skills .skill-badge'))
        .map(el => el.textContent.trim());

    // Join skills with comma (or space, depending on how you're saving them)
    document.getElementById('skills').value = selectedSkills.join(', ');
});

