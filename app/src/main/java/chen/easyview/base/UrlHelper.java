package chen.easyview.base;

public interface UrlHelper {


    String BASE_URL = "http://192.168.1.46:8080/";


    String TODO_URL = "/easyview/todo.php";

//
//    /**
//     * 首页顶部
//     */
//    String TABHOME_TOP=BASE_URL + "/Api/GetIndexData.ashx";
//
//    /**
//     * 话题详情webVIEW
//     */
//    String WEIPIN = BASE_URL + "/ent/WeiPin.ashx";
//
//    /**
//     * 企业发布职位
//     */
//    String ENTJOBLIST = BASE_URL + "/WebPage/EntJobList.aspx";
//
//    /**
//     * 获取人脉链
//     */
//    String GETFRIENDPATH = BASE_URL + "/Q/GetFriendPath.ashx";
//
//    /**
//     * 话题举报
//     */
//    String REPOSTMSG = BASE_URL + "/Q/ReportMsg.ashx";
//    /**
//     * 获取话题列表
//     */
//    String GetTopicList = BASE_URL + "/Q/GetTopicList.ashx";
//    /**
//     * 获取分层岗位
//     */
//    String TEST_SELECT = BASE_URL + "/Ent/GetKeywordByLevel.ashx";
//    /**
//     * 获取共同好友
//     */
//    String GetCommonFriends = BASE_URL + "/Q/GetCommonFriends.ashx";
//
//    /**
//     * 发布职位获取部门信息
//     */
//    String SELECTDEPT = BASE_URL + "/ent/JobManage/selectDeptJson.ashx";
//
//    /**
//     * 搜索获取联想数据
//     */
//    String KEYSEARCH = BASE_URL + "/ent/sys/SKeywordSearch.ashx";
//
//    /**
//     * 获取APP更新信息
//     */
//    String CHECKUPDATE = BASE_URL + "/Api/GetAppInfo.ashx";
//    /**
//     * 修改密码
//     */
//    String CHANGEPWD = BASE_URL + "/ent/changepwd.ashx";
//    /**
//     * 添加部门  删除部门  获取部门信息
//     */
//    String MGDEPT = BASE_URL + "/ent/MgDept.ashx";
//    /**
//     * 获取企业联系人信息
//     */
//    String CHECK_ENT = BASE_URL + "/ent/JobManage/JobOper_CheckEnt.ashx";
//
//
//    /**
//     * 获取企业相关数据信息
//     */
//    String CHECK_ENT_COUNT = BASE_URL + "/ent/EntInfoCount.ashx";
//    /**
//     * 获取简历详情
//     */
//    String SHOWRESUME = BASE_URL + "/ent/showresume.ashx";
//    /**
//     * 获取并显示联系方式
//     */
//    String SHOW_SUME_LINK = BASE_URL + "/ent/showresumelink.ashx";
//    /**
//     * 获取话题评论带图片
//     */
//    String GET_TOPIC_COMMONT = BASE_URL + "/Q/GetTopicDiscuss.ashx";
//    /**
//     * 获取话题详情，主界面
//     */
//    String GET_TOPIC_DETAIL = BASE_URL + "/Q/GetTopic.ashx";
//    /**
//     * 转发话题
//     */
//    String TURN_TOPIC = BASE_URL + "/Q/TurnTopic.ashx";
//    /**
//     * 简历收藏夹更改收藏夹
//     */
//    String MOVEFAVORS = BASE_URL + "/ent/movefavors.ashx";
//    /**
//     * 验证码登录
//     */
//    String MobileLogin = BASE_URL + "/Ent/MobileLogin.ashx";
//    /**
//     * 充值 订单号
//     */
//    String GET_RECHARGE = BASE_URL + "/bid/recharge.ashx";
//
//    /**
//     * 获取招聘会筛选器下拉内容
//     */
//    String GETMYJOBFAIR = BASE_URL + "/Ent/GetMyJobFair.ashx";
//
//    /**
//     *
//     */
//    String GET_BID_DETAIL = BASE_URL + "/bid/getbiddetail.ashx";
//    /**
//     * 获取竞价排名情况
//     */
//    String GET_PROMO_STATUS = BASE_URL + "/bid/getpromostatus.ashx";
//    /**
//     * 获取竞价排名的城市列表
//     */
//    String GET_BID_PROMOCITY = BASE_URL + "/bid/getpromocity.ashx";
//    /**
//     * 关闭精准，智能推广
//     */
//    String GET_PAUSE_BIDDING = BASE_URL + "/bid/PauseBidding.ashx";
//    /**
//     * 获取置顶推广售价
//     */
//    String GET_TOP_STICK_PLAN = BASE_URL + "/bid/gettopstickplan.ashx";
//    /**
//     * 开启置顶推广
//     */
//    String GET_START_TOP_BIDDING = BASE_URL + "/bid/startbidding.ashx";
//    /**
//     * 关闭置顶推广
//     */
//    String GET_PAUSE_TOP_BIDDING = BASE_URL + "/bid/PauseBidding.ashx";
//    /**
//     * 获取竞价报告数据
//     */
//    String GET_SUMMARY_INFO = BASE_URL + "/bid/getsummaryinfo.ashx";
//
//    /**
//     * 开通精准，智能推广
//     */
//    String GET_START_BIDDING = BASE_URL + "/bid/startbidding.ashx";
//    /**
//     * 查看职位竞价统计
//     */
//    String GET_CHECK_REPORT = BASE_URL + "/bid/checkreport.ashx";
//    /**
//     * 获取最高最低价
//     */
//    String GET_CHECK_TOP_PRICE = BASE_URL + "/bid/checktopprice.ashx";
//    /**
//     * 获取余额预算消费
//     */
//    String GET_CHECK_BLANCE = BASE_URL + "/bid/checkbalance.ashx";
//    /**
//     * 获取工作圈单条具体信息
//     */
//    String GET_CIRCLE_Message = BASE_URL + "/Q/GetMsg.ashx";
//    /**
//     * 图片服务器地址
//     */
//    String BASE_URL_PHOTO = "http://pic.stzp.cn";
//    /**
//     * 圈子图片地址
//     */
//    String PHOTO_URL_CIRCLE = BASE_URL_PHOTO + "/uploadfiles/Circle/";
//
//    /**
//     * 获取当前的站点
//     */
////    String NOW_SITE = BASE_URL_OLD + "/3g/V2/Ajax/GetSite.ashx";
//    String NOW_SITE = BASE_URL + "/Api/NewGetSite.ashx";
//
//    /**
//     * 上传企业头像
//     */
//    String CHECK_PICTURE = BASE_URL + "/Ent/UpLogo.ashx";
//    /**
//     * 用户操作接口
//     */
//    String HX_GETUSEROPER = BASE_URL + "/News1/UserOper.ashx";
//
//    /**
//     * 环形用户信息接口
//     */
//    String HX_GETUSERINFO = BASE_URL + "/News1/GetInfo.ashx";
//    /**
//     * 用户列表,用于搜索添加好友
//     */
//    String HX_GETUSER = BASE_URL + "/News1/GetUser.ashx";
//    /**
//     * 简历详情接口
//     */
//    String SHOW_RESUME = BASE_URL + "/3g/enta/showresume.ashx";
//    /**
//     * 新简历详情接口
//     */
//    String SHOW_RESUME_DETAIL = BASE_URL + "/Ent/showresume.ashx";
//    /**
//     * 公司所属行业
//     */
//    String GET_COMPANY_FIELD = BASE_URL + "/Api/GetComData.ashx";
//    /**
//     *
//     */
//    String GET_COMPANY_INFO = BASE_URL + "/Ent/EntBasicInfo.ashx";
//    /**
//     * 行政区及热门地标接口
//     */
//    String ADMIN_HOTPOINT = BASE_URL + "/3g/jw/BMapHotAreaList.ashx";
//
//    /**
//     * 关键字查询 eg：http://www.bczp.cn/3g/V2/Ajax/SKeywordSearch.ashx?keyword=a
//     */
//    String KEYWORD_QUERY = BASE_URL + "/3g/V2/Ajax/SKeywordSearch.ashx";
//
//    String SKeywordSearch = BASE_URL + "/jw/SKeywordSearch.ashx";
//    /**
//     * 周边求职者
//     */
//    String NEAR_JOB = BASE_URL + "/Ent/NearJw.ashx";
//    /**
//     * 热门关键词
//     */
//    String GET_COMPANY_COMMON_DATA = BASE_URL + "/Api/GetComData.ashx";
//    /**
//     * 刷新简历
//     */
//    String REFRESH_RESUME = BASE_URL + "/Ent/Refresh.ashx";
//    /**
//     * 刷新简历倒计时
//     */
//    String REFRESH_RESUME_COUNTDOWN = BASE_URL + "/Ent/GetRefreshCountdown.ashx";
//    /**
//     * 获取site code
//     */
//    String GET_SITE_CODE = BASE_URL + "/Api/GetCityIdByKeyword.ashx";
//    /**
//     * 获取本地通首页推荐商家列表
//     * 参数: cityid
//     */
//    String GET_RECOMMENDED_ADS = BASE_URL + "/benditong/AdIndexPro.ashx";
//    /**
//     * 传入经纬度获取本地通城市信息
//     * 参数：lat, lng
//     */
//    String GET_CITY_BY_COORDINATES = BASE_URL + "/benditong/getcity.ashx";
//    /**
//     * 获取本地通首页横幅
//     */
//    String GET_LOCAL_GUIDE_BANNER = BASE_URL + "/benditong/AdIndexBar.ashx";
//    /**
//     * 获取本地通首页分类
//     */
//    String GET_LOCAL_GUIDE_CATEGORY = BASE_URL + "/benditong/ProType.ashx";
//    /**
//     * 获取本地通城市列表
//     */
//    String GET_LOCAL_GUIDE_CITY_LIST = BASE_URL + "/benditong/city.ashx";
//    /**
//     * 获取本地通城市信息
//     */
//    String GET_LOCAL_GUIDE_CITY = BASE_URL + "/benditong/GetACity.ashx";
//    /**
//     * 获取本地通子类详细列表
//     */
//    String GET_LOCAL_GUIDE_CATEGORY_DETAIL_LIST = BASE_URL + "/benditong/Pro.ashx";
//    /**
//     * 获取本地通合作商家详情
//     */
//    String GET_LOCAL_GUIDE_COLLABORATOR_DETAIL = BASE_URL + "/benditong/GetAPro.ashx";
//
//    /**
//     * 邀请加盟
//     */
//    String SEND_INVITATION = BASE_URL + "/ent/invites.ashx";
//
//    /**
//     * 收藏夹-添加建立
//     */
//    String ADD_RESUME_TO_FAVORITE = BASE_URL + "/Ent/addfavors.ashx";
//
//    /**
//     * 更新求职者备注
//     */
//    String UPDATE_REMARKS = BASE_URL + "/Ent/bz.ashx";
//
//    /**
//     * 收藏夹操作
//     */
//    String FAVORITE_LIST_OPS = BASE_URL + "/Ent/favorlistjson.ashx";
//
//    /**
//     * 收藏夹 -操作简历
//     */
//    String FAVORITE_OPS = BASE_URL + "/Ent/favorjson.ashx";
//
//    /**
//     * 获取职位搜索器列表
//     */
//    String SEARCHLIST = BASE_URL + "/ent/SearchList.ashx";
//
//
//    /**
//     * 删除面试通知
//     */
//    String DELETE_INTERVIEW = BASE_URL + "/Ent/InterviewDel.ashx";
//
//    /**
//     * 发送面试通知
//     */
//    String SEND_INTERVIEW_NOTICE = BASE_URL + "/Ent/InterviewSend.ashx";
//
//    /**
//     * 删除收到的简历
//     */
//    String DELETE_RECEIVED_RESUME = BASE_URL + "/Ent/recresumedel.ashx";
//
//    /**
//     * 筛选界面 职位列表
//     */
//    String SELECTJOBJSON = BASE_URL + "/ent/selectJobJson.ashx?lx=1";
//
//    /**
//     * 筛选界面 时间范围
//     */
//    String SELECTDATELIST = BASE_URL + "/ent/selectdatelist.ashx";
//
//
//    /**
//     * 公司信息
//     */
//    String ENTINFO = BASE_URL + "/Ent/EntInfo.ashx";
//
//    /**
//     * 筛选界面 部门列表
//     */
//    String SELECTDEPTJSON = BASE_URL + "/ent/JobManage/selectDeptJson.ashx";
//
//    /**
//     * 邀请面试操作
//     */
//    String INVITATION_OPS = BASE_URL + "/Ent/totalinvjson.ashx";
//
//    /**
//     * 搜索求职者
//     */
//    String SEARCH_JW = BASE_URL + "/Ent/searchjw.ashx";
//
//    /**
//     * 根据职位匹配id，获取简历列表
//     */
//    String GET_RESUME_BY_JOBSN = BASE_URL + "/Ent/matching.ashx";
//
//    /**
//     * 我浏览过谁
//     */
//    String MY_BROWSING_HISTORY = BASE_URL + "/Ent/queryhistoryjson.ashx";
//
//    /**
//     * 谁浏览过我
//     */
//    String COMPLANY_BROWSED_HISTORY = BASE_URL + "/Ent/historyJson.ashx";
//
//    /**
//     * 收到的简历
//     */
//    String GET_RECEIVED_RESUME = BASE_URL + "/Ent/recresumejson.ashx";
//
//    /**
//     * 标记简历
//     */
//    String MARK_RECEIVED_RESUME = BASE_URL + "/ent/SetResSign.ashx";
//
//    /**
//     * 推荐的简历
//     */
//    String GET_RECOMMENDED_RESUME = BASE_URL + "/Ent/recommendjson.ashx";
//
//    /**
//     * 已发面试通知记录
//     */
//    String GET_SENT_INTERVIEW_RECORDS = BASE_URL + "/Ent/InterviewJson.ashx";
//
//    /*
//     * 问题反馈
//     */
//    String SUBMIT_FEEDBACK = BASE_URL + "/Ent/FeedBackAdd.ashx";
//
//    /**
//     * 回复留言
//     */
//    String REPLY_FEEDBACK = BASE_URL + "/Ent/guestbookadd.ashx";
//
//    /**
//     * 获取信息列表
//     */
//    String GET_INFO_LIST = BASE_URL + "/ART/ArticleListNew.ashx";
//
//    /**
//     * 修改收藏夹名称
//     */
//    String EDIT_FAVORITE_LIST = BASE_URL + "/Ent/editfavorlist.ashx";
//
//    /**
//     * 添加收藏夹
//     */
//    String ADD_FAVORITE_LIST = BASE_URL + "/Ent/addfavorlist.ashx";
//
//    /**
//     * 获取所有职位列表
//     */
//    String POS_LIST_OPS = BASE_URL + "/Ent/JobManage/mgjobjson.ashx";
//    /**
//     * 获取所有职位详情
//     */
//    String JOB_INFO = BASE_URL + "/Ent/JobManage/JobInfo.ashx";
//
//    /**
//     * 获取职位匹配列表
//     */
//    String GET_MATCHED_POS_LIST = BASE_URL + "/Ent/selectJobJson.ashx";
//
//    /**
//     * 登录
//     */
//    String LOGIN = BASE_URL + "/Ent/getlogin.ashx";
//
//    String GET_CIRCLE_LIST = BASE_URL + "/Q/GetMsgList.ashx";
//
//    String GET_CIRCLE_CATEGORY = BASE_URL + "/Q/Circle.ashx";
//
//    /**
//     * 获取TA朋友圈数量
//     */
//    String GET_CIRCLE_COUNT = BASE_URL + "/Q/GetMsgCount.ashx";
//
//    /**
//     * 上传通讯录
//     */
//    String TRANSFER_CONTACTS = BASE_URL + "/q/TongXunUpdate.ashx";
//    /**
//     * 判断是否上传过
//     */
//    String JUDEG_TRANSFER_CONTACTS = BASE_URL + "/Q/IsExistsAddrList.ashx";
//    /**
//     * 获取返回的通讯录好友列表
//     */
//    String GET_CONTACTS = BASE_URL + "/News1/UserOper.ashx";
//
//    /**
//     * 邀请好友
//     */
//    String INVITE_CONTACTS = BASE_URL + "/q/InviteFriends.ashx";
//
//    /**
//     * 发布评论
//     */
//    String PUBLISH_COMMENT = BASE_URL + "/Q/Discuss.ashx";
//
//
//    /**
//     * 点赞
//     */
//    String SET_LIKE = BASE_URL + "/Q/Like.ashx";
//
//    /**
//     * 发布信息&转发信息
//     */
//    String PUBLISH_MSG = BASE_URL + "/Q/SendMsg.ashx";
//
//    /**
//     * 获取评论列表
//     */
//    String GET_COMMENT_LIST = BASE_URL + "/Q/GetDiscussList.ashx";
//
//    /**
//     * 获取新消息列表
//     */
//    String GET_NEW_MSG_LIST = BASE_URL + "/Q/GetNewNotice.ashx";
//
//
//    /**
//     * 获取职圈的缩略图可以通过参数：W=xx设置
//     */
//    String GET_CIRCLE_PIC_THUMB = BASE_URL + "/News1/GetLogo.ashx";
//
//    /**
//     * 感兴趣的人
//     */
//    String INTERESTING_FRIENDS = BASE_URL + "/Q/MaybeYouKnow.ashx";
//    /**
//     * 头像
//     */
//    String USER_PICTURE = BASE_URL_PHOTO + "/uploadfiles/jw/";
//
//    /**
//     * 获取面试通知基本信息
//     */
//    String INTERVIEW_SEND = BASE_URL + "/ent/InterviewSendCheck.ashx";
//    /**
//     * 删除动态
//     */
//    String DELETE_DYNAMIC = BASE_URL + "/Q/DelMsg.ashx";
//    /**
//     * 获取n度人脉
//     */
//    String GET_DEG_FRIENDS = BASE_URL + "/Q/GetDegFriends.ashx";
//
//
//    /**
//     * 信鸽
//     */
//    String XG_PUSH = BASE_URL + "/Ent/SetXgKey.ashx";
//
//    /**
//     * 获取某人的标签
//     */
//    String GET_TAGS_BY_PERSON = BASE_URL + "/Q/GetTagsByPerson.ashx";
//
//    /**
//     * 获取某类标签
//     */
//    String GET_TAGS_BY_TYPE = BASE_URL + "/Q/GetTagsByType.ashx";
//
//    /**
//     * 获取标签
//     */
//    String ADD_TAG = BASE_URL + "/Q/AddTag.ashx";
//
//    /**
//     * 删除自己贴的标签
//     */
//    String REMOVE_TAG_RELATION = BASE_URL + "/Q/RemoveTagRelation.ashx";
//
//    /**
//     * 删除别人贴的标签
//     */
//    String REMOVE_TAG = BASE_URL + "/Q/RemoveTag.ashx";
//
//    /**
//     * 获取城市和工作名称和code
//     */
//    String LOCAL_CACHE = BASE_URL + "/ent/checkversion.ashx";
//
//    /**
//     * 搜索好友
//     */
//    String GET_SEARCHUSER = BASE_URL + "/Q/SearchUser.ashx";
//
//    /**
//     * 获取收到的求职简历
//     */
//    String GET_RECRESUME = BASE_URL + "/ent/recresumejson.ashx";
//
//    /**
//     * 添加职位
//     */
//    String JOBOPER = BASE_URL + "/ent/JobManage/JobOper.ashx";
//
//    /**
//     * 简历详情
//     */
//    String JOBDETAIL = UrlHelper.BASE_URL + "/WebPage/JobDetail.aspx";
//
//    /**
//     * 企业详情
//     */
//    String ENTDETAIL = UrlHelper.BASE_URL + "/WebPage/EntDetail.aspx";
//
//    /**
//     * 邀请加盟
//     */
//
//    /**
//     * 获取搜索器子项的详细信息
//     */
//    String SEARCH = UrlHelper.BASE_URL + "/ent/Search.ashx";
//
//    /**
//     * 删除简历搜索器,单个删除
//     */
//    String DeleteSearchor = UrlHelper.BASE_URL + "/ent/DelSearch.ashx";
//
//
//    /**
//     * 自动刷新详情
//     */
//    String AUTOREFRESH = BASE_URL + "/Ent/AutoRefresh.ashx";
//
//    /**
//     * 获取企业标签
//     */
//    String GETTAGSBYTYPE = BASE_URL + "/Q/GetTagsByType.ashx";
//
//    /**
//     * 注册
//     */
//    String REGISTE_URL = BASE_URL + "/ent/Register.ashx"; //注册
//    /**
//     * 设置
//     */
//    String APP_SETTING = BASE_URL + "/ent/SettingEdit.ashx";
//    /**
//     * 设置详情
//     */
//    String APP_SETTING_DETAIL = BASE_URL + "/ent/SettingDetail.ashx";
//
//    /**
//     * 首页 --职场主题 ,,匿名八卦,,同道中人
//     */
//    String TOPIC_BA_GUA_SAME_PERSON= BASE_URL + "/Q/GetIndexCircleMsg.ashx";
}
