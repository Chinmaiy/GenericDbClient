$(document).ready(function () {

  $(".clicker").click(function(){
    var lnk, player;

    //check if any player exists
    if($("#theplayingone").length > 0){
      //check if this is the player and update display (pause)
      if($(this).is("#theplayingone")){
          player = document.getElementsByTagName('audio')[0];
          player.pause();
          $("#theplayingone").html("<span class=\"glyphicon glyphicon-music\"></span><span> Play</span>");
          $(this).removeAttr('id');
      }
      else {
        //remove previous player
        $("#theplayingone").html("<span class=\"glyphicon glyphicon-music\"></span><span> Play</span>");
        $("#theplayingone").removeAttr('id');
        //get the link
        lnk = $(this).attr("lnk");
        //set the audio
        $("audio").attr("src", lnk);
        player = document.getElementsByTagName('audio')[0];
        player.play();
        //set the view
        $(this).attr('id', 'theplayingone');
        $(this).html("<span class=\"glyphicon glyphicon-headphones\"></span><span> Pause</span>");
      }
    }
    else {
      lnk = $(this).attr("lnk");
      //set the audio
      $("audio").attr("src", lnk);
      player = document.getElementsByTagName('audio')[0];
      player.play();
      //set the view
      $(this).attr('id', 'theplayingone');
      $(this).html("<span class=\"glyphicon glyphicon-headphones\"></span><span> Pause</span>");

    }


});
});
