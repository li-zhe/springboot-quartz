import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// 公共路由
export const constantRoutes = [
  {
    path: '',
    redirect: '/job'
  },
  {
    path: '/job',
    component: (resolve) => require(['@/views/job/index'], resolve),
    name: 'Job',
    meta: { title: '定时任务', icon: 'time', affix: true }
  },
  {
    path: '/log',
    hidden: true,
    component: (resolve) => require(['@/views/job/log'], resolve),
    name: 'JobLog',
    meta: { title: '定时任务记录', activeMenu: '/job' }
  }
]

export default new Router({
  mode: 'history',
  base: 'view',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
