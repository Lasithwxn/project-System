// basic JS for sidebar toggle and counters

function toggleSidebar() {
    const sb = document.getElementById('sidebar');
    sb.classList.toggle('-translate-x-full');
}

// counter animation
function animateCounter(id, end) {
    const el = document.getElementById(id);
    if (!el) return;
    let start = 0;
    const step = Math.ceil(end/50);
    const interval = setInterval(() => {
        start += step;
        if (start >= end) { el.textContent = end; clearInterval(interval); }
        else el.textContent = start;
    }, 20);
}

window.addEventListener('DOMContentLoaded', () => {
    // example values set in templates
    animateCounter('totalItems', parseInt(document.getElementById('totalItems')?.textContent||0));
});