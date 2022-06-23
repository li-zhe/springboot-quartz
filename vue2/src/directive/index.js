import dialogDrag from './dialog/drag'
import dialogDragWidth from './dialog/dragWidth'
import dialogDragHeight from './dialog/dragHeight'

const install = function(Vue) {
  Vue.directive('dialogDrag', dialogDrag)
  Vue.directive('dialogDragWidth', dialogDragWidth)
  Vue.directive('dialogDragHeight', dialogDragHeight)
}

if (window.Vue) {
  Vue.use(install) // eslint-disable-line
}

export default install
