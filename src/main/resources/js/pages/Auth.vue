<template>
    <v-form
    >
        <v-text-field
                v-model="name"
                label="Name"
        ></v-text-field>
        <v-btn
                :disabled="name === ''"
                color="orange"
                @click="registration"
        >REGISTRATION
        </v-btn>
        <v-btn
                :disabled="name === ''"
                color="success"
                @click="login"
        >SignIN
        </v-btn>
    </v-form>
</template>

<script>
    import {mapActions} from 'vuex'
    import {connect} from '@util/ws'
    import userApi from "@api/user"

    export default {
        name: "Auth",

        computed: {
            name: {
                get() {
                    return this.$store.state.user.name
                },
                set(value) {
                    this.setName(value)
                }
            }
        },

        methods: {
            ...mapActions('user', ['setName']),

            async registration() {
              try {
                let signUp = await userApi.signUp(this.name);
                const res = signUp.bodyText;
                console.log(res);
              } catch (error) {
                console.error(`${error.bodyText}`)
              }
            },

            async login() {
              try {
                const response = await userApi.signIn(this.name)
                console.log(`Success login. Status response ${response.status}`)
                await connect(this.name)
                await this.$router.push({path: '/'})
              } catch (error){
                console.error(`Login error. Status response ${error.status}`)
              }
            }
        },

    }
</script>

<style scoped>

</style>