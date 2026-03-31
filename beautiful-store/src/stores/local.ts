import {defineStore} from "pinia";

export const loginEmailStore = defineStore("loginEmail", {
    state: () => ({
        email: "",
        password: "",
    }),
    getters: {
        getEmail(state) {
            return state.email;
        },
        getPassword(state) {
            return state.password;
        },
    },
    actions: {
        setEmail(email: string) {
            this.email = email;
        },
        setPassword(password: string) {
            this.password = password;
        },
    },
});


export const loginUserTypeStore = defineStore("loginUserType", {
    state: () => ({
        userType: "",
    })
    , getters: {
        getUserType(state) {
            return state.userType;
        },
    },
    actions: {
        setUserType(userType: string) {
            this.userType = userType;
        },
    },
});