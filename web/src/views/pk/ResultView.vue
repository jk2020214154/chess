<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.result === 'win' "> 
            你赢了
        </div>

        <div class="result-board-text" v-else-if="$store.state.pk.result === 'lose' "> 
            你输了
        </div>

        <div class="result-board-text" v-else>

            
        </div>
        <div class="result-board-btn">
            <button @click="restart" type="button" class="btn btn-warning">
                重新开始
            </button>
        </div>



    </div>



</template>



<script>

import { useStore } from 'vuex';

export default{
    setup(){
        const store=useStore();

        const restart=()=>{
            
            setTimeout(() => {
                store.commit("updateResult","");
                store.commit("updateStatus","ungameing");
                
                }, 300);

        }

        return {
            restart,
        }



    }
}


</script>


<style>

div.result-board{
    height: 30vh;
    width: 30vw;
    background-color: blueviolet;
    position: absolute;
    top:30vh;
    left:35vw;
}
div.result-board-text{
    text-align: center;
    color: white;
    font-weight: 600;
    font-size: 50px;
    font-style: italic;
    padding-top: 5vh;
}

div.result-board-btn{
    padding-top: 7vh;
    text-align: center;
}



</style>