import Vue from 'vue'
import VueResource from 'vue-resource'
import {API} from "@util/common";

Vue.use(VueResource)

const user = Vue.resource(API + '{action}');
function formData(username,password) {
    let formData = new FormData();
    formData.set("username",username)
    if (password === true){
        formData.set("password","123")
    }
    return formData
}
export default {
    signUp: (username) => user.save({action: 'registration'},formData(username)),
    signIn: (username) => user.save({action: 'login'},formData(username,true)),

}