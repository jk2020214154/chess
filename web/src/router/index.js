import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkindexView.vue';
import RecordIndexView from '../views/record/RecordindexView.vue';
import RankListIndexView from '../views/ranklist/RanklistindexView.vue';
import UserBotIndexView from '../views/user/bot/UserBotindexView.vue';
import UserAccountLoginView from '../views/user/account/UserAccountLoginView.vue';
import UserAccountRegisterView from '../views/user/account/UserAccountRegisterView.vue';

import NotFound from '../views/error/NotFound.vue';


const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndexView,
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RankListIndexView,
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
  },
  {
    path: "/404/",
    name: "404",
    component: NotFound,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
