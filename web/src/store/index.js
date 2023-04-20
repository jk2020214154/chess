import { createStore } from 'vuex'
import ModelUser from './user'
import ModelPk from './pk'


export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModelUser,
    pk: ModelPk,
  }
})
