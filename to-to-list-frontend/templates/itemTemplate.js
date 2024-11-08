export function itemTemplate(item) {
    return `
        <li class="list-item" data-id="${item.id}">
            <span class="list-title">${item.title}</span>
            <span class="list-description">${item.description}</span>
            <span class="list-date">${new Date(item.endsAt).toLocaleDateString()}</span>
            <button class="btn-update" data-id="${item.id}">Edit</button>
            <button class="btn-delete" data-id="${item.id}">Delete</button>
        </li>
    `;
}