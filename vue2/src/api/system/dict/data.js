import request from '@/utils/request'


// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  let res={
    data:[]
  };
  switch (dictType){
    case "sys_job_group":
      res.data=[{"dictValue":"DEFAULT","listClass":"","dictSort":1,"remark":"默认分组","params":{},"dictType":"sys_job_group","dictLabel":"默认","isDefault":"Y","dictCode":10,"status":"0"},{"dictValue":"SYSTEM","listClass":"","dictSort":2,"remark":"系统分组","params":{},"dictType":"sys_job_group","dictLabel":"系统","isDefault":"N","dictCode":11,"status":"0"}]
      break;
    case "sys_job_status":
      res.data=[{"dictValue":"0","listClass":"primary","dictSort":1,"remark":"正常状态","params":{},"dictType":"sys_job_status","dictLabel":"正常","isDefault":"Y","cssClass":"","dictCode":8,"status":"0"},{"dictValue":"1","listClass":"danger","dictSort":2,"remark":"停用状态","params":{},"dictType":"sys_job_status","dictLabel":"暂停","isDefault":"N","cssClass":"","dictCode":9,"status":"0"}]
      break;
    case "sys_common_status":
      res.data=[{"dictValue":"0","listClass":"primary","dictSort":1,"remark":"正常状态","params":{},"dictType":"sys_common_status","dictLabel":"成功","isDefault":"N","cssClass":"","dictCode":27,"status":"0"},{"dictValue":"1","listClass":"danger","dictSort":2,"remark":"停用状态","params":{},"dictType":"sys_common_status","dictLabel":"失败","isDefault":"N","cssClass":"","dictCode":28,"status":"0"}]
      break;
    default:
      break;
  }
  return Promise.resolve(res);
  // return request({
  //   url: '/system/dict/data/type/' + dictType,
  //   method: 'get'
  // })
}
