<template>
    <div>
        <v-row class="d-flex justify-center align-center" >
            <div class="d-flex ma-12">
                <your-desk></your-desk>
            </div>
            <div class="d-flex ma-6">
              <opponent></opponent>
            </div>
        </v-row>
    </div>
</template>

<script>
    import opponent from "../Components/OpponentDesk.vue"
    import yourDesk from "../Components/YouDesk.vue"

    export default {
        name: "BattleField",
        components: {
            opponent,
            yourDesk,
        },
        data() {
            return {}
        }
    }

</script>

<style scoped>

</style>