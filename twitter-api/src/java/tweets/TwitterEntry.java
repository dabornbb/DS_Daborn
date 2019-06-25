/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

/**
 *
 * @author nbnb
 */
public class TwitterEntry{
  long _id;  
  String _text;
  int _fromUserId, _toUserId;
  String _languageCode;
  
  public TwitterEntry() { }
  public void setId(long id) { _id = id; }
  public void setText(String text) { _text = text; }
  public void setFromUserId(int id) { _fromUserId = id; }
  public void setToUserId(int id) { _toUserId = id; }
  public void setLanguageCode(String languageCode) { _languageCode = languageCode; }

  public long getId() {
      return _id; }
  public String getText() { return _text; }
  public int getFromUserId() { return _fromUserId; }
  public int getToUserId() { return _toUserId; }
  public String getLanguageCode() { return _languageCode; }

  public String toString() {
    return "[Tweet, id: "+_id +", text='"+_text+"', from: "+_fromUserId+", to: "+_toUserId+", lang: "+_languageCode+"]";
  }
}