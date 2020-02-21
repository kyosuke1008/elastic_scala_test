$(function() {
  $("#btn").click(function() {
    $("#overlay").fadeIn();　/*ふわっと表示*/
  });
  $("#close").click(function() {
    $("#overlay").fadeOut();　/*ふわっと消える*/
  });
});