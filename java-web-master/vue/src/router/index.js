import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/common/Index";
import UserList from "@/views/user/list"
import UserAdd from "@/views/user/add"
import UserUpdate from "@/views/user/update"
import Layout from "@/views/common/layout"

Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    component: () => import("../views/common/login")
  },
  {
    path: '/enroll',
    component: () => import("../views/common/enroll")
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        component: Index
      },

      //---------------------------user------------------------
      {
        path: 'user/list',
        name: 'UserList',
        component: UserList
      },
      {
        path: 'user/add',
        name: 'UserAdd',
        component: UserAdd
      },
      {
        path: 'user/update',
        name: 'UserUpdate',
        component: UserUpdate
      },
      {
        path: 'user/info',
        component: () => import("../views/common/info")
      },

      //---------------------------course------------------------
      {
        path: 'course/list',
        name: 'CourseList',
        component: () => import("../views/course/list")
      },
      {
        path: 'course/add',
        name: 'CourseAdd',
        component: () => import("../views/course/add")
      },
      {
        path: 'course/update',
        name: 'CourseUpdate',
        component: () => import("../views/course/update")
      },

      //---------------------------teach------------------------
      {
        path: 'teach/list',
        name: 'TeachList',
        component: () => import("../views/teach/list")
      },

      //----------------------------stu-------------------------
      {
        path: 'stu/list',
        name: 'StuList',
        component: () => import("../views/stu/searchList")
      },
      {
        path: 'stu/myCourse',
        component: () => import("../views/stu/myCourse")
      },
      {
        path: 'stu/myScore',
        component: () => import("../views/stu/myScore")
      },

      //----------------------------tea-------------------------
      {
        path: 'tea/myCourse',
        component: () => import("../views/tea/myCourse")
      },
      {
        path: 'tea/score',
        component: () => import("../views/tea/recordScore")
      },
      {
        path: 'tea/manageScore',
        component: () => import("../views/tea/manageScore")
      },


    ]
  },
];

const router = new VueRouter({
  mode: 'history',
  routes
});

import Cookies from 'js-cookie';


router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/enroll' || to.path === '/forgetPassword') {
    return  next();
  }
  let user = Cookies.get('user');
  // 增加判断条件
  if (user == null && (to.path !== '/login' || to.path !== '/enroll' || to.path !== '/forgetPassword')){
    return next('/login');
  }
  next();
});


export default router
