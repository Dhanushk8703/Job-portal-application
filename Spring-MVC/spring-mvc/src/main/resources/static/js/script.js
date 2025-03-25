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

// ✅ Function to smoothly transition between steps
function updateStep() {
    document.querySelectorAll('.container-box').forEach((step, index) => {
        const stepNumber = index + 1;

        if (stepNumber === currentStep) {
            step.classList.remove('hidden', 'fade-out', 'slide-left', 'slide-right');
            step.classList.add('fade-in'); // ✅ Smooth fade-in for active step
        } else {
            step.classList.remove('fade-in');
            step.classList.add('hidden'); // ✅ Hide inactive steps immediately
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
function toggleSection(sectionId) {
    let section = document.getElementById(sectionId);

    // ✅ Toggle visibility
    if (section.style.display === 'none' || section.style.display === '') {
        section.style.display = 'flex';
    } else {
        section.style.display = 'none';
    }
}

document.addEventListener("DOMContentLoaded", function () {
    let optionGroups = document.querySelectorAll(".option-group");

    // ✅ Initially hide all option groups except the first one
    optionGroups.forEach((section, index) => {
        section.style.display = index === 0 ? "flex" : "none";
    });

    // ✅ Handle option selection and group transitions
    optionGroups.forEach((group, index) => {
        if (!group.classList.contains("budget-group")) {
            group.querySelectorAll(".option").forEach(option => {
                option.addEventListener("click", function () {
                    let input = this.querySelector("input");
                    if (input) {
                        input.checked = true;

                        // Remove 'selected' class from other options in the same group
                        document.querySelectorAll(`input[name="${input.name}"]`).forEach(radio => {
                            radio.parentElement.classList.remove("selected");
                        });

                        // Add 'selected' class to the clicked option
                        this.classList.add("selected");

                        // Hide current option group and show the next one
                        setTimeout(() => {
                            group.style.display = "none";

                            // ✅ Show the next option group (if available)
                            let nextGroup = optionGroups[index + 1];
                            if (nextGroup && nextGroup.classList.contains("option-group")) {
                                nextGroup.style.display = "flex";
                            }
                        }, 300);
                    }
                });
            });
        }
    });

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
});
