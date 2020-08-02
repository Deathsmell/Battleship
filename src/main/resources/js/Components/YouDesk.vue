<template>
    <div class="ma-5">
        <table>
            <tr>
                <th v-for=" y in fields.length + 1">{{y - 1}}</th>
            </tr>
            <tr
                    v-for="i in fields.length "
            >
                <td class="coordinate">{{i}}</td>
                <td
                        v-for="f in fields[i - 1].length"
                        v-bind:class="{'field-void': whatColor(i,f), 'field-ship': !whatColor(i,f) }"
                        v-bind:id="i+'-'+f"
                        @click="isShip(i,f)"
                >{{fields[i-1][f - 1]}}
                </td>
            </tr>
        </table>
        <div
                class="mt-3"
        >
            <v-btn
                    @click="clearField()"
            >Очистить поле
            </v-btn>
            <v-btn
                    @click="validateField()"
            >Проверить
            </v-btn>
        </div>
        <v-snackbar
                v-model="snackbar"
                bottom="bottom"
                :color="color"
                multi-line
                timeout="6000"
                top
        >
            {{text}}
        </v-snackbar>
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
                isYourBoard: true,
                snackbar: false,
                text: 'Error',
                color: 'error',
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
            whatColor(array, index) {
                return this.fields[array - 1][index - 1] === 0
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
                        this.snackbar = true;
                        this.text = response.status + ' Success validation'
                        this.color = 'alert'
                        // console.log(snackbar + ' ' + response.status)
                    })
                    .catch(error => {
                        console.log(error);
                        this.snackbar = true;
                        this.text = error.status
                        this.color = 'error'
                    })
            }
        }
    }
</script>

<style lang="sass" scoped>
    table, th, td
        border: 1px solid black

        th, td
            height: 25px
            width: 25px
            text-align: center

        .coordinate
            font-weight: bold


        .field-void
            background: #1565C0
            font: 0 Arial


        .field-ship
            background: #37474F
            font: 0 Arial

</style>