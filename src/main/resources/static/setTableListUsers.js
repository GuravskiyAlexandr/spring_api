window.onload = setUser();

function getRoles(rolesArray) {
    let roles = '';
    rolesArray.forEach(role => {
        roles = roles + ' ' + role.role.substring(5)
    });
    return roles
}

function addCell(tr, text) {
    let td = tr.insertCell();
    td.innerText = text;
    return td;
}

async function setUser() {
    let user;
    try {
        const response = await fetch('/user');
        user = await response.json();
        console.log('usersAuthentication ok');
    } catch (error) {
        console.error('Error usersAuthentication :', error);
    }

    if (getRoles(user.roles).includes('ADMIN')){
        await setUsersToTable();
    }

    let email = document.getElementById("emailHeader");
    let roles = document.getElementById("rolesHeader");
    email.innerText = user.email;
    roles.innerText = getRoles(user.roles);


    const checkRole = obj => obj.role === 'ROLE_ADMIN';
    let buttonUsersList = document.getElementById("v-pills-home-tab");
    let buttonUser = document.getElementById("v-pills-profile-tab");
    let adminPanel = document.getElementById("v-pills-home");
    let userPanel = document.getElementById("v-pills-profile");
    if (user.roles.some(checkRole)) {
        buttonUsersList.style.display = 'block';
        adminPanel.classList.add('active','show');
    } else {
        adminPanel.classList.remove('show','active');
        buttonUser.classList.add('active');
        buttonUsersList.style.display = 'none';
        userPanel.style.display = 'block';
        userPanel.style.opacity = '1';
    }
    let table = document.getElementById("userTable");

    let row = table.insertRow();
    addCell(row, user.id);
    addCell(row, user.firstName);
    addCell(row, user.lastName);
    addCell(row, user.age);
    addCell(row, user.email);
    addCell(row, getRoles(user.roles));
}


async function setUsersToTable() {
    let date;
    try {
        const response = await fetch('/admin/users');
        date = await response.json();
        console.log('ok:');

    } catch (error) {
        console.error('Error :', error);
    }
    setTableUser(date)
}

function setTableUser(date) {
    let table = document.getElementById("usersListTable");
    date.forEach((user, index) => {
        let row = table.insertRow();
        table.getElementsByTagName('tr')[index + 1].setAttribute('id', 'tr' + user.id);
        addCell(row, user.id);
        addCell(row, user.firstName);
        addCell(row, user.lastName);
        addCell(row, user.age);
        addCell(row, user.email);
        addCell(row, getRoles(user.roles));
        setButton(row, user.id, "Edit", "btn-primary", "/admin/edit", "POST");
        setButton(row, user.id, "Delete", "btn-danger", "/admin/delete", "DELETE");
    });
}

function setButton(tr, userId, value, color, path, method) {
    let button = document.createElement('a');
    button.title = value;
    button.innerText = value;
    button.setAttribute(
        'onclick', "setParamForm('" + path + "','" + method + "'," + userId + ",'" + value + "')");
    button.setAttribute('data-bs-toggle', "modal");
    button.setAttribute('data-bs-target', "#exampleModal");
    button.setAttribute('class', "btn " + color);
    button.setAttribute('style', "width: 80px;");
    button.type = 'button';
    let td = tr.insertCell();
    td.appendChild(button);
    return td;
}