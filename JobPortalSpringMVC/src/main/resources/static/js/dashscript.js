function toggleMenu() {
    let menu = document.querySelector(".floating-menu");
    let icon = menu.querySelector("i");

    menu.classList.toggle("open");

    if (menu.classList.contains("open")) {
        icon.classList.remove("fa-plus");
        icon.classList.add("fa-times"); // Change to "×" icon
    } else {
        icon.classList.remove("fa-times");
        icon.classList.add("fa-plus"); // Revert back to "+"
    }
}

document.addEventListener("DOMContentLoaded", () => {
    sessionStorage.setItem("employerEmail", /*[[${email}]]*/ '');
    
    const employerEmail = sessionStorage.getItem("employerEmail");

    if (!employerEmail) return;

    const apiBaseUrl = "http://localhost:9090"; // 👈 Your backend port

    fetch(`${apiBaseUrl}/api/jobs/drafts?employerEmail=${employerEmail}`)
        .then(res => res.json())
        .then(jobs => {
            const container = document.getElementById("draftJobsContainer");
            container.innerHTML = "";

            if (jobs.length === 0) {
                container.innerHTML = `<p class="text-muted">No draft jobs found.</p>`;
                return;
            }

            jobs.forEach((job, index) => {
                const card = document.createElement("div");
                card.className = "job-card";
                card.innerHTML = `
                    <div class="job-icon"><i class="fa-solid fa-briefcase"></i></div>
                    <div>
                        <div class="job-title">${job.jobTitle}</div>
                        <div class="job-description">${job.jobDescription || "Draft job post - Add details to your draft"}</div>
                    </div>
                    <div class="job-actions">
                        <button class="btn btn-outline-primary" onclick="toggleDetails('details${index}')">
                            <i class="fa-solid fa-eye"></i> View Details
                        </button>
                        <button class="btn btn-outline-success" onclick="postJob(${job.id})">
                            <i class="fa-solid fa-paper-plane"></i> Post Job
                        </button>
                        <button class="btn btn-outline-danger" onclick="deleteDraft(${job.id})">
                            <i class="fa-solid fa-trash"></i> Remove Draft
                        </button>
                    </div>
                    <div class="job-details hidden mt-3 p-3 border rounded bg-light" id="details${index}">
                        <p><strong>Job Title:</strong> ${job.jobTitle}</p>
                        <p><strong>Description:</strong> ${job.jobDescription}</p>
                        <p><strong>Location:</strong> ${job.location}</p>
                        <p><strong>Work Mode:</strong> ${job.workMode}</p>
                        <p><strong>Eligibility:</strong> ${job.eligibility}</p>
                        <p><strong>Required Skills:</strong> ${job.requiredSkills}</p>
                        <p><strong>Experience Level:</strong> ${job.experience}</p>
                        <p><strong>Status:</strong> ${job.jobStatus}</p>
                        <p><strong>Salary Range:</strong> $${job.minSalary} - $${job.maxSalary}</p>
                        <p><strong>Deadline:</strong> ${job.applicationDeadline}</p>
                    </div>
                `;
                container.appendChild(card);
            });
        })
        .catch(err => {
            console.error("Error fetching draft jobs:", err);
            const container = document.getElementById("draftJobsContainer");
            container.innerHTML = `<p class="text-danger">Failed to load draft jobs.</p>`;
        });
});

// Toggle detail section
function toggleDetails(id) {
    const detailDiv = document.getElementById(id);
    detailDiv.classList.toggle("hidden");
}

// Post a job
function postJob(id) {
    const apiBaseUrl = "http://localhost:9090";
    fetch(`${apiBaseUrl}/api/jobs/post/${id}`, {
        method: 'POST'
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        location.reload();
    })
    .catch(err => {
        alert("Error posting job.");
        console.error(err);
    });
}

// Delete a draft
function deleteDraft(id) {
    const apiBaseUrl = "http://localhost:9090";
    if (confirm("Are you sure you want to delete this draft?")) {
        fetch(`${apiBaseUrl}/api/jobs/delete/${id}`, {
            method: 'DELETE'
        })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            location.reload();
        })
        .catch(err => {
            alert("Error deleting draft.");
            console.error(err);
        });
    }
}
