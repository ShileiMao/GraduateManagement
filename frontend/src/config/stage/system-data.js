const systemDataRouter = {
  route: null,
  name: null,
  title: '系统数据管理',
  type: 'folder',
  icon: 'el-icon-s-management',
  filePath: 'view/system/data',
  order: null,
  inNav: true,
  permission: ['超级管理员独有权限'],
  children: [
    {
      name: null,
      title: '学院管理',
      type: 'view', // 取 route 为默认加载页
      inNav: true,
      route: '/college/list',
      filePath: 'view/system/data/college.vue',
    },

    {
      name: null,
      title: '专业管理',
      type: 'view', // 取 route 为默认加载页
      inNav: true,
      route: '/major/list',
      filePath: 'view/system/data/major.vue',
    },

    {
      name: null,
      title: '班级管理',
      type: 'view', // 取 route 为默认加载页
      inNav: true,
      route: '/class/list',
      filePath: 'view/system/data/class.vue',
    },

    {
      name: null,
      title: '学生管理',
      type: 'folder', // 取 route 为默认加载页
      icon: 'el-icon-user-solid',
      inNav: true,
      children: [
        {
          title: '添加学生',
          type: 'view',
          name: 'StudentCreate',
          route: '/student/add',
          filePath: 'view/system/data/student/student-create.vue',
          inNav: true,
          permission: ['创建学生'],
        },
        {
          title: '学生列表',
          type: 'view',
          name: 'StudentList',
          route: '/student/list',
          filePath: 'view/system/data/student/student-list.vue',
          inNav: true,
        },
      ],
    },
    {
      name: null,
      title: '教师管理',
      type: 'folder', // 取 route 为默认加载页
      icon: 'iconfont icon-huiyuanguanli',
      inNav: true,
      children: [
        {
          title: '添加教师',
          type: 'view',
          name: 'TeacherCreate',
          route: '/teacher/add',
          filePath: 'view/system/data/teacher/teacher-create.vue',
          inNav: true,
          permission: ['创建教师'],
        },
        {
          title: '教师列表',
          type: 'view',
          name: 'TeacherList',
          route: '/teacher/list',
          filePath: 'view/system/data/teacher/teacher-list.vue',
          inNav: true,
        },
      ],
    },
  ],
}

export default systemDataRouter
