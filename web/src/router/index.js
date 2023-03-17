import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkindexView.vue';
import RecordIndexView from '../views/record/RecordindexView.vue';
import RankListIndexView from '../views/ranklist/RanklistindexView.vue';
import UserIndexView from '../views/user/UserindexView.vue';
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
    path: "/user/",
    name: "user_index",
    component: UserIndexView,
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
