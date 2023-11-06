package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressSearchTrie {
    private final AddressNode root;

    public AddressSearchTrie() {
        this.root = new AddressNode("root", '.');
    }

    public void insertAddress(String word, String refId) {
        if (Utils.isNumeric(word)) return;
        AddressNode pCrawl = this.root;
        for (int i = word.length() - 1; i >= 0; i--) {
            pCrawl.setChild(word.charAt(i), new AddressNode(word.substring(i), word.charAt(i)));
            pCrawl = pCrawl.getChild(word.charAt(i));
        }
        pCrawl.setLeafRefId(refId);
    }

    public List<String> searchAddressId(String word) {
        AddressNode pCrawl = this.root;
        HashMap<Character, AddressNode> tempChildren = new HashMap<>();
        String tempWord = "";
        List<String> retListRefId = new ArrayList<>();
        for (int i = word.length() - 1; i >= 0; i--) {
            if (pCrawl.getChild(word.charAt(i)) == null) {
                if(i - 1 >= 0) {
                    if(pCrawl.getChild(word.charAt(i-1)) == null) {
                        tempChildren = pCrawl.getChildren();
                        tempWord = word.substring(0, i);
                        break;
                    } else {
                        pCrawl = pCrawl.getChild(word.charAt(--i));
                    }
                } else {
                    tempChildren = pCrawl.getChildren();
                    tempWord = word.substring(0, i);
                    break;
                }
            } else {
            	pCrawl = pCrawl.getChild(word.charAt(i));
            }
            if (!pCrawl.getLeafRefId().isBlank() && !pCrawl.getLeafRefId().isEmpty()) {
                retListRefId.add(pCrawl.getLeafRefId());
            }
        }
        if (tempWord.isEmpty()) {
            if (!pCrawl.getLeafRefId().isBlank() && !pCrawl.getLeafRefId().isEmpty()) {
                retListRefId.add(pCrawl.getLeafRefId());
            }
        } else {
            for (Map.Entry<Character, AddressNode> entry : tempChildren.entrySet()) {
                AddressNode pCrawlSubString = entry.getValue();
                System.out.println(tempWord);
                for (int j = tempWord.length() - 1; j >= 0; j--) {
                    if (pCrawlSubString.getChild(tempWord.charAt(j)) == null) {
                        break;
                    }
                    pCrawlSubString = pCrawlSubString.getChild(tempWord.charAt(j));
                }
                if (!pCrawlSubString.getLeafRefId().isBlank() && !pCrawlSubString.getLeafRefId().isEmpty()) {
                    retListRefId.add(pCrawlSubString.getLeafRefId());
                }
            }
        }
        return retListRefId;
    }

    public String normalSearch(String word) {
        AddressNode pCrawl = this.root;
        for (int i = word.length() - 1; i >= 0; i--) {
            if (pCrawl.getChild(word.charAt(i)) == null) {
                return "";
            }
            pCrawl = pCrawl.getChild(word.charAt(i));
        }
        return pCrawl.getLeafRefId();
    }
}
