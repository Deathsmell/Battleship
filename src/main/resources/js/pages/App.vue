<template>
    <v-app>
        <BattleField></BattleField>
    </v-app>
</template>

<script>
    import BattleField from "../Components/BattleField.vue";

    export default {
        name: "App",
        components: {
            BattleField,
        },
        data: () => ({}),
    }
</script>

<style scoped>

</style>