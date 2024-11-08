import { getAllUserItems, createNewItem, updateItem, deleteItem } from "./api.js";
import { itemTemplate } from "../../templates/itemTemplate.js";

let editingItemId = null;

document.addEventListener("DOMContentLoaded", () => {
    const ownerId = "85acd700-06b5-42f9-9d78-3183828ff849";

    displayItems(ownerId);

    const formContainer = document.getElementById("formContainer");
    const addItemForm = document.getElementById("addItemForm");
    const newItemButton = document.getElementById("newItem");
    const cancelButton = document.getElementById("cancelButton");

    newItemButton.addEventListener("click", () => {
        editingItemId = null;
        resetForm();
        formContainer.classList.remove("hidden");
    });

    cancelButton.addEventListener("click", () => {
        formContainer.classList.add("hidden");
    });

    addItemForm.addEventListener("submit", async (event) => {
        const itemData = readFormData();

        try {
            if (editingItemId) {
                const updatedItem = await updateItem(editingItemId, itemData);
                updateItemInDOM(updatedItem);
            } else {
                const newItem = await createNewItem(itemData);
                addItemToDOM(newItem);
            }

            formContainer.classList.add("hidden");
        } catch (error) {
            console.error(error);
            alert('Failed to save item');
        }
    });
});

document.getElementById("itemList").addEventListener("click", async (event) => {
    const itemId = event.target.getAttribute("data-id");

    if (event.target.classList.contains("btn-delete")) {
        try {
            await deleteItem(itemId);
            removeItemFromDOM(itemId);
        } catch (error) {
            console.error("Error deleting item:", error);
            alert("Failed to delete item");
        }
    } else if (event.target.classList.contains("btn-update")) {
        editingItemId = itemId;
        const itemData = document.querySelector(`li[data-id="${itemId}"]`);
        populateFormWithItemData(itemData);
        formContainer.classList.remove("hidden");
    }
});

function readFormData() {
    const formData = {};

    formData["title"] = document.getElementById("title").value;
    formData["description"] = document.getElementById("description").value;
    formData["endsAt"] = new Date(document.getElementById("endsAt").value).toISOString();
    formData["ownerId"] = "85acd700-06b5-42f9-9d78-3183828ff849";

    return formData;
}

function addItemToDOM(item) {
    const itemList = document.getElementById("itemList");
    itemList.insertAdjacentHTML("beforeend", itemTemplate(item));
}

function populateFormWithItemData(itemData) {
    document.getElementById("title").value = itemData.querySelector(".list-title").textContent;
    document.getElementById("description").value = itemData.querySelector(".list-description").textContent;
    document.getElementById("endsAt").value = new Date(itemData.querySelector(".list-date").textContent).toISOString().slice(0, 10);
}

function resetForm() {
    document.getElementById("title").value = "";
    document.getElementById("description").value = "";
    document.getElementById("endsAt").value = "";
}

function updateItemInDOM(updatedItem) {
    const itemElement = document.querySelector(`li[data-id="${updatedItem.id}"]`);
    if (itemElement) {
        itemElement.outerHTML = itemTemplate(updatedItem);
    }
}

function removeItemFromDOM(itemId) {
    const itemElement = document.querySelector(`li[data-id="${itemId}"]`);

    if (itemElement) {
        itemElement.remove();
    }
}

async function displayItems(ownerId) {
    const items = await getAllUserItems(ownerId);

    if (items && Array.isArray(items.content)) {
        items.content.forEach(item => {
            addItemToDOM(item);
        });
    } else {
        console.log("No items found or error in API response.");
    }
}