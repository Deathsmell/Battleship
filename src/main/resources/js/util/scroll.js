
export function scrollToBottom () {
    setTimeout(function () {
        let scrollContainer = document.querySelector('#chat-window')
        let isScrolledToBottom = scrollContainer.scrollHeight - scrollContainer.clientHeight <= scrollContainer.scrollTop + 1
        if (!isScrolledToBottom) { scrollContainer.scrollTop = scrollContainer.scrollHeight }
    }, 201)
}