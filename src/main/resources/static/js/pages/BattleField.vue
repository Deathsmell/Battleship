<template>
    <div>
        <table class="field">
            <tr>
                <th v-for=" y in fields.length + 1">{{y - 1}}</th>
            </tr>
            <tr
                    v-for="i in fields.length "
            >
                <td class="coordinate">{{i}}</td>
                <td
                        v-for="f in fields[i - 1].length"
                        v-bind:class="{'field-void': whatColor(fields[i-1][f -1]), 'field-ship': !whatColor(fields[i-1][f - 1]) }"
                        v-bind:id="i+'-'+f"
                        @click="isShip(i,f)"
                >{{fields[i-1][f - 1]}}
                </td>
            </tr>
        </table>
        <button @click="clearField()" type="submit">Очистить поле</button>
        <button @click="validateField()" type="submit">Проверить</button>
    </div>
</template>

<script>
    let API = 'http://localhost:8080/';

    export default {
        name: "BattleField",
        data() {
            return {
                hasVoid: false,
                hasShip: false,
                fields: [
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                ]
            }
        },
        methods: {
            getField() {
                this.$http.get(API + 'field').then(res => {
                    res.body.forEach((array, index) => {
                        this.fields.splice(index, 1, array)
                    })
                })
            },
            whatColor(cell) {
                return cell === 0
            },
            isShip(array, index) {
                let temp = this.fields[array - 1][index - 1]
                temp = temp === 0 ? 1 : 0
                this.fields[array - 1].splice(index - 1, 1, temp)
            },
            clearField() {
                let array = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                this.fields.forEach(value => {
                    value.splice(0, value.length)
                    array.forEach(zero => {
                        value.push(zero)
                    })
                })
            },
            validateField() {
                this.$http.post(API + 'validationField', this.fields)
                    .then(response => {
                        console.log(response.status);
                    })
                    .catch(error => {
                        console.log(error.status);
                    })
            }
        }
    }
</script>

<style scoped>
    table, th, td {
        border: 1px solid black;
    }

    th, td {
        height: 25px;
        width: 25px;
        text-align: center;
    }

    /*table {*/
    /*    position: absolute;*/
    /*    top: 25%;*/
    /*    left: 40%;*/
    /*}*/

    .coordinate {
        font-weight: bold;
    }

    .field-void {
        background: blue;
        font: 0 Arial;
    }

    .field-ship {
        background: black;
        font: 0 Arial;
    }
</style>