const API_URL = "http://localhost:8080/to-do-list";

export async function createNewItem(item) {
    try {
        const response = await fetch(`${API_URL}/items`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item)
        });

        if (!response.ok) {
            throw new Error("Failed to create item");
        }
        
        return await response.json();
    } catch (error) {
        console.error("Error creating item:", error);
        return null;
    }
}

export async function updateItem(itemId, itemData) {
    try {
        const response = await fetch(`${API_URL}/items/${itemId}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(itemData)
        });

        if (!response.ok) {
            throw new Error("Failed to create item");
        }
        
        return await response.json();
    } catch (error) {
        console.error("Error creating item:", error);
        return null;
    }
}

export async function getAllUserItems(ownerId, page = 0, size = 10) {
    try {
        const response = await fetch(`${API_URL}/items?ownerId=${ownerId}&page=${page}&size=${size}`);
        
        if (!response.ok) {
            throw new Error("Failed to fetch user items");
        }

        return await response.json();
    } catch (error) {
        console.log("Error fetching api", error);
        return { content: [] };
    }
}

export async function deleteItem(itemId) {
    try {
        const response = await fetch(`${API_URL}/items/${itemId}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            throw new Error("Failed to delete item");
        }
    } catch (error) {
        console.error("Error deleting item:", error);
    }
}