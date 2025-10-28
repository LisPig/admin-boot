package com.sz.core.common.sensitive;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 敏感词过滤器
 * 使用DFA算法实现敏感词过滤
 */
public class SensitiveWordFilter {
    
    /**
     * 敏感词树的根节点
     */
    private final Map<Object, Object> sensitiveWordMap = new ConcurrentHashMap<>();
    
    /**
     * 敏感词库
     */
    private Set<String> sensitiveWords = new HashSet<>();
    
    /**
     * 默认的敏感词库（常规敏感词）
     */
    private static final String[] DEFAULT_SENSITIVE_WORDS = {
        "暴力", "色情", "赌博", "毒品", "反动", "邪教", "诈骗", "谣言", 
        "恐怖主义", "分裂主义", "极端主义", "违法", "违规", "侵权", 
        "盗版", "造假", "走私", "洗钱", "行贿", "受贿", "贪污", "腐败",
        "卖淫", "嫖娼", "吸毒", "聚众", "闹事", "煽动", "颠覆", "渗透",
        "间谍", "泄密", "机密", "绝密", "国家机密", "军事机密", "商业机密",
        "隐私", "偷拍", "偷窥", "跟踪", "骚扰", "侮辱", "诽谤", "造谣",
        "虚假", "欺诈", "坑蒙拐骗", "传销", "直销", "非法集资", "高利贷",
        "套路贷", "校园贷", "现金贷", "暴力催收", "威胁", "恐吓", "勒索",
        "绑架", "拘禁", "虐待", "伤害", "杀人", "投毒", "放火", "爆炸",
        "危险物品", "放射性", "传染病", "疫情", "瘟疫", "病毒", "病菌",
        "假药", "劣药", "有毒食品", "有害食品", "食品安全", "药品安全",
        "产品质量", "假冒伪劣", "三无产品", "黑作坊", "黑工厂", "黑窝点",
        "环境污染", "大气污染", "水污染", "土壤污染", "噪音污染", "光污染",
        "核污染", "电磁辐射", "化学污染", "生物污染", "固体废物", "危险废物",
        "医疗事故", "医闹", "医托", "号贩子", "药贩子", "器官买卖", "人体试验",
        "基因编辑", "克隆人", "代孕", "拐卖", "拐骗", "诱拐", "绑架儿童",
        "儿童色情", "儿童虐待", "校园霸凌", "校园暴力", "性侵", "性骚扰",
        "家庭暴力", " domestic violence", " domestic abuse", "家暴",
        "网络暴力", "人肉搜索", "网络攻击", "黑客", "木马", "病毒软件",
        "恶意软件", "钓鱼网站", "网络诈骗", "电信诈骗", "刷单", "兼职诈骗",
        "投资诈骗", "理财诈骗", "保险诈骗", "贷款诈骗", "信用卡诈骗",
        "身份盗窃", "个人信息泄露", "数据泄露", "隐私泄露", "监控", "监听",
        "窃听", "窃取", "黑客攻击", "网络入侵", "系统漏洞", "安全漏洞",
        "色情网站", "成人网站", "黄色网站", "低俗", "淫秽", "下流", "猥亵",
        "性交易", "性服务", "性用品", "情趣用品", "成人用品", "避孕套",
        "伟哥", "壮阳药", "催情药", "迷奸药", "摇头丸", "冰毒", "海洛因",
        "大麻", "鸦片", "罂粟", "K粉", "摇头丸", "神仙水", "蓝精灵",
        "赌博网站", "网络赌博", "赌场", "老虎机", "百家乐", "斗地主",
        "炸金花", "德州扑克", "麻将", "牌九", "骰子", "彩票", "时时彩",
        "北京赛车", "北京赛车pk10", "北京赛车飞盘", "北京赛车开奖",
        "六合彩", "香港六合彩", "地下六合彩", "私彩", "黑彩", "外围",
        "外围盘", "盘口", "庄家", "水房", "洗码", "抽水", "返点",
        "代理", "总代理", "股东", "大股东", "操盘手", "内鬼", "内应",
        "黑客改单", "黑客入侵", "黑客破解", "黑客攻击", "黑客软件",
        "黑客教程", "黑客工具", "黑客基地", "黑客联盟", "黑客论坛",
        "黑客网站", "黑客组织", "黑客集团", "网络犯罪", "网络黑产",
        "网络赌博", "网络色情", "网络诈骗", "网络传销", "网络非法集资",
        "网络洗钱", "你妈","你他妈","逼","傻逼","傻叉"
    };
    
    /**
     * 构造函数，初始化默认敏感词库
     */
    public SensitiveWordFilter() {
        initSensitiveWords(Arrays.asList(DEFAULT_SENSITIVE_WORDS));
    }
    
    /**
     * 构造函数，使用自定义敏感词库
     * @param words 敏感词列表
     */
    public SensitiveWordFilter(List<String> words) {
        initSensitiveWords(words);
    }
    
    /**
     * 初始化敏感词库
     * @param words 敏感词列表
     */
    private void initSensitiveWords(List<String> words) {
        this.sensitiveWords.addAll(words);
        // 构建敏感词树
        buildSensitiveWordTree();
    }
    
    /**
     * 构建敏感词树
     */
    private void buildSensitiveWordTree() {
        sensitiveWordMap.clear();
        for (String word : sensitiveWords) {
            if (word == null || word.trim().isEmpty()) {
                continue;
            }
            
            Map<Object, Object> currentMap = (Map<Object, Object>) sensitiveWordMap;
            for (int i = 0; i < word.length(); i++) {
                char keyChar = word.charAt(i);
                Object wordMap = currentMap.get(keyChar);
                
                if (wordMap != null) {
                    currentMap = (Map<Object, Object>) wordMap;
                } else {
                Map<Object, Object> newWordMap = new HashMap<>();
                    newWordMap.put("isEnd", false);
                    currentMap.put(keyChar, newWordMap);
                    currentMap = newWordMap;
                }
                
                if (i == word.length() - 1) {
                    currentMap.put("isEnd", true);
                }
            }
        }
    }
    
    /**
     * 检查文本是否包含敏感词
     * @param text 待检查文本
     * @return true:包含敏感词, false:不包含敏感词
     */
    public boolean containsSensitiveWord(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < text.length(); i++) {
            int length = checkSensitiveWord(text, i);
            if (length > 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获取文本中的所有敏感词
     * @param text 待检查文本
     * @return 敏感词列表
     */
    public List<String> getSensitiveWords(String text) {
        List<String> result = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return result;
        }
        
        for (int i = 0; i < text.length(); i++) {
            int length = checkSensitiveWord(text, i);
            if (length > 0) {
                result.add(text.substring(i, i + length));
                i = i + length - 1;
            }
        }
        return result;
    }
    
    /**
     * 替换文本中的敏感词
     * @param text 待处理文本
     * @param replaceChar 替换字符
     * @return 处理后的文本
     */
    public String replaceSensitiveWords(String text, char replaceChar) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder(text);
        for (int i = 0; i < result.length(); i++) {
            int length = checkSensitiveWord(result.toString(), i);
            if (length > 0) {
                for (int j = 0; j < length; j++) {
                    result.setCharAt(i + j, replaceChar);
                }
                i = i + length - 1;
            }
        }
        return result.toString();
    }
    
    /**
     * 检查从指定位置开始是否包含敏感词
     * @param text 文本
     * @param beginIndex 开始位置
     * @return 敏感词长度，0表示未匹配到
     */
    private int checkSensitiveWord(String text, int beginIndex) {
        int length = 0;
        Map<Object, Object> currentMap = (Map<Object, Object>) sensitiveWordMap;
        boolean isEnd = false;
        
        for (int i = beginIndex; i < text.length(); i++) {
            char word = text.charAt(i);
            Map<Object, Object> wordMap = (Map<Object, Object>) currentMap.get(word);
            
            if (wordMap != null) {
                length++;
                currentMap = wordMap;
                isEnd = (Boolean) wordMap.get("isEnd");
            } else {
                break;
            }
        }
        
        return isEnd ? length : 0;
    }
    
    /**
     * 添加敏感词
     * @param word 敏感词
     */
    public void addSensitiveWord(String word) {
        if (word != null && !word.trim().isEmpty()) {
            sensitiveWords.add(word);
            buildSensitiveWordTree();
        }
    }
    
    /**
     * 移除敏感词
     * @param word 敏感词
     */
    public void removeSensitiveWord(String word) {
        if (word != null && !word.trim().isEmpty()) {
            sensitiveWords.remove(word);
            buildSensitiveWordTree();
        }
    }
    
    /**
     * 获取所有敏感词
     * @return 敏感词集合
     */
    public Set<String> getAllSensitiveWords() {
        return new HashSet<>(sensitiveWords);
    }
}