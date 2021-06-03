async function sendForm(idForm) {
    let form = document.getElementById(idForm);

    let user = {};
    for (let i = 0; i < form.elements.length; i++) {
        let item = form.elements.item(i);
        if (item.name === 'roles') {
            user[item.name] = new Set();
            item.options[0].selected ? user[item.name].add(item.options[0].value) : '';
            item.options[1].selected ? user[item.name].add(item.options[1].value) : '';
            user[item.name] = Array.from(user[item.name])
        } else if (item.value !== '')
            user[item.name] = item.value;

    }

    let path = form.getAttribute('action');
    const method = form.getAttribute('method');

    const headers = new Headers({
        'Content-Type': 'application/json',
    });
    if (method === 'DELETE'){
        path +='/'+user.id;
    }
    let userNew;
    try {
        const response = await fetch(path, {
            method: method,
            headers,
            credentials: 'include',
            body: JSON.stringify(user)
        });
        console.log('Ok :');
        userNew = await response.json();
        if (method !== 'DELETE') {
            await userSetTable(user, userNew, form)
        } else {
            if (userNew)
            document.getElementById('tr' + user.id).remove();
        }

    } catch (error) {
        console.error('error :', error);
    }
}

async function userSetTable(user, userNew, form) {
    let tr = document.getElementById('tr' + user.id);

    if (tr != null) {
        let td = tr.getElementsByTagName('td');
        td[0].innerText = userNew.id;
        td[1].innerText = userNew.firstName;
        td[2].innerText = userNew.lastName;
        td[3].innerText = userNew.age;
        td[4].innerText = userNew.email;
        td[5].innerText = getRoles(userNew.roles);
        form.reset();
    } else {
        await setTableUser([userNew]);
        document.getElementById('nav-home-tab').click();
        form.reset();
    }
}